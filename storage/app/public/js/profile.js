// Preview the chosen profile picture
document.getElementById('profile_picture').addEventListener('change', function (e) {
    const file = e.target.files[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = function (event) {
        document.getElementById('profilePicturePreview').src = event.target.result;
    };
    reader.readAsDataURL(file);
});

// Handle remove picture button
document.getElementById('removePicture')?.addEventListener('click', function () {
    if (!confirm('Are you sure you want to remove your profile picture?')) return;

    const removeInput   = document.createElement('input');
    removeInput.type    = 'hidden';
    removeInput.name    = 'remove_profile_picture';
    removeInput.value   = '1';
    document.querySelector('.profile-form').appendChild(removeInput);

    document.getElementById('profilePicturePreview').src =
        document.getElementById('profilePicturePreview').dataset.default;

    document.getElementById('profile_picture').value = '';
});
