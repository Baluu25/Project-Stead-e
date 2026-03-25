document.querySelectorAll('.filter-btn').forEach(btn => {
    btn.addEventListener('click', function () {
        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');
        applyFilters();
    });
});

document.querySelector('.achievement-input').addEventListener('input', applyFilters);

function applyFilters() {
    const filter = document.querySelector('.filter-btn.active').textContent.trim().toLowerCase();
    const search = document.querySelector('.achievement-input').value.toLowerCase();

    document.querySelectorAll('.achievement-block').forEach(block => {
        const status = block.dataset.status;
        const name   = block.dataset.name;

        const matchFilter =
            filter === 'all' ||
            (filter === 'completed'   && status === 'completed') ||
            (filter === 'in progress' && (status === 'in-progress' || status === 'locked'));

        const matchSearch = !search || name.includes(search);

        block.style.display = matchFilter && matchSearch ? '' : 'none';
    });

    document.querySelectorAll('.category-section').forEach(section => {
        const visible = section.querySelectorAll('.achievement-block:not([style*="none"])').length;
        section.style.display = visible > 0 ? '' : 'none';
    });
}
