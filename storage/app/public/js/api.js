const csrfToken = document.querySelector('meta[name="csrf-token"]').content;

function apiGet(url) {
    return fetch(url, {
        headers: { 'Accept': 'application/json' }
    }).then(r => r.json());
}

function apiPost(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
    }).then(r => r.json());
}

function apiPut(url, data) {
    return fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
    }).then(r => r.json());
}

function apiDelete(url) {
    return fetch(url, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    });
}
