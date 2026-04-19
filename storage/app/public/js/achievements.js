// filter buttons
document.querySelectorAll('.filter-btn').forEach(function (btn) {
    btn.addEventListener('click', function () {

        document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
        this.classList.add('active');
        applyFilters();
    });
});

// run filters when input change
document.querySelector('.achievement-input').addEventListener('input', applyFilters);

// filter logic
function applyFilters() {
    const filter = document.querySelector('.filter-btn.active').textContent.trim().toLowerCase();
    const search = document.querySelector('.achievement-input').value.toLowerCase();

    document.querySelectorAll('.achievement-block').forEach(function (block) {
        const status      = block.dataset.status;
        const name        = block.dataset.name;
        const progressBar = block.querySelector('.achievement-progress');
        const progress    = progressBar ? (parseFloat(progressBar.style.width) || 0) : 0;

        // match active filter category
        let matchesFilter = true;
        if (filter === 'completed') {
            matchesFilter = status === 'completed';
        } else if (filter === 'in progress') {
            matchesFilter = progress > 0 && progress < 100;
        }

        // match search field
        const matchesSearch = !search || name.includes(search);
        block.style.display = matchesFilter && matchesSearch ? '' : 'none';
    });

    // hide category if their achievements are hidden
    document.querySelectorAll('.category-section').forEach(function (section) {
        const visibleCount = section.querySelectorAll('.achievement-block:not([style*="none"])').length;
        section.style.display = visibleCount > 0 ? '' : 'none';
    });
}
