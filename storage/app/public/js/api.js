// csrf token
const csrfToken = document.querySelector('meta[name="csrf-token"]').content;

// GET request, return json
function apiGet(url) {
    return fetch(url, {
        headers: { 'Accept': 'application/json' }
    }).then(response => response.json());
}

// POST request with jsons data, return json
function apiPost(url, data) {
    return fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
    }).then(response => response.json());
}

// PUT request
function apiPut(url, data) {
    return fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify(data)
    }).then(response => response.json());
}

// DELETE request
function apiDelete(url) {
    return fetch(url, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        }
    });
}
