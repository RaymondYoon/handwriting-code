const postForm = document.querySelector('#post-form');
const postList = document.querySelector('#post-list');
const postDetail = document.querySelector('#post-detail');
const formMessage = document.querySelector('#form-message');
const submitButton = document.querySelector('#submit-button');
const refreshButton = document.querySelector('#refresh-button');

let selectedPostId = null;

function formatDate(dateTime) {
    if (!dateTime) {
        return '작성 시간 없음';
    }

    return new Intl.DateTimeFormat('ko-KR', {
        dateStyle: 'medium',
        timeStyle: 'short'
    }).format(new Date(dateTime));
}

function showMessage(message, type) {
    formMessage.textContent = message;
    formMessage.className = `message ${type}`;
}

function createPostCard(post) {
    const button = document.createElement('button');
    button.type = 'button';
    button.className = 'post-card';
    button.classList.toggle('selected', post.id === selectedPostId);

    const title = document.createElement('h3');
    title.textContent = post.title;

    const content = document.createElement('p');
    content.textContent = post.content;

    const meta = document.createElement('p');
    meta.className = 'post-meta';
    meta.textContent = `${post.author} · ${formatDate(post.createdAt)}`;

    button.append(title, content, meta);
    button.addEventListener('click', () => loadPost(post.id));
    return button;
}

function renderPosts(posts) {
    postList.replaceChildren();

    if (posts.length === 0) {
        const empty = document.createElement('p');
        empty.className = 'empty-detail';
        empty.textContent = '아직 작성된 게시글이 없습니다.';
        postList.append(empty);
        return;
    }

    [...posts].reverse().forEach((post) => postList.append(createPostCard(post)));
}

function renderDetail(post) {
    postDetail.replaceChildren();

    const title = document.createElement('h3');
    title.className = 'detail-title';
    title.textContent = post.title;

    const meta = document.createElement('p');
    meta.className = 'post-meta';
    meta.textContent = `작성자 ${post.author} · ${formatDate(post.createdAt)}`;

    const content = document.createElement('p');
    content.className = 'detail-content';
    content.textContent = post.content;

    postDetail.append(title, meta, content);
}

async function loadPosts() {
    postList.textContent = '게시글을 불러오는 중입니다…';

    try {
        const response = await fetch('/api/posts');
        if (!response.ok) {
            throw new Error('게시글 목록을 불러오지 못했습니다.');
        }

        renderPosts(await response.json());
    } catch (error) {
        postList.textContent = error.message;
    }
}

async function loadPost(id) {
    try {
        const response = await fetch(`/api/posts/${id}`);
        if (!response.ok) {
            throw new Error('게시글을 불러오지 못했습니다.');
        }

        selectedPostId = id;
        renderDetail(await response.json());
        await loadPosts();
    } catch (error) {
        postDetail.textContent = error.message;
    }
}

postForm.addEventListener('submit', async (event) => {
    event.preventDefault();
    submitButton.disabled = true;
    showMessage('게시글을 등록하는 중입니다…', '');

    const formData = new FormData(postForm);
    const post = Object.fromEntries(formData.entries());

    try {
        const response = await fetch('/api/posts', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(post)
        });

        if (!response.ok) {
            throw new Error('게시글 등록에 실패했습니다.');
        }

        const createdPost = await response.json();
        postForm.reset();
        showMessage(`게시글 #${createdPost.id}가 등록되었습니다.`, 'success');
        await loadPost(createdPost.id);
    } catch (error) {
        showMessage(error.message, 'error');
    } finally {
        submitButton.disabled = false;
    }
});

refreshButton.addEventListener('click', loadPosts);
loadPosts();
