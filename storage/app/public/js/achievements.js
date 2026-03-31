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

        const progressBar = block.querySelector('.achievement-progress');
        let progress = 0;
        if (progressBar) {
            progress = parseFloat(progressBar.style.width) || 0;
        }

        let matchFilter = true;
        
        if (filter === 'all') {
            matchFilter = true;
        } else if (filter === 'completed') {
            matchFilter = status === 'completed';
        } else if (filter === 'in progress') {
            matchFilter = progress > 0 && progress < 100;
        }

        const matchSearch = !search || name.includes(search);

        block.style.display = matchFilter && matchSearch ? '' : 'none';
    });

    document.querySelectorAll('.category-section').forEach(section => {
        const visible = section.querySelectorAll('.achievement-block:not([style*="none"])').length;
        section.style.display = visible > 0 ? '' : 'none';
    });
}