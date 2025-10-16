let currentSpecialistId = null;
let selectedTimeSlot = null;

function filterBySpecialite() {
    const filter = document.getElementById('specialiteFilter').value;
    const rows = document.querySelectorAll('.specialist-row');
    let visibleCount = 0;

    rows.forEach(row => {
        const specialite = row.getAttribute('data-specialite');
        if (filter === '' || specialite === filter) {
            row.style.display = '';
            visibleCount++;
        } else {
            row.style.display = 'none';
        }
    });

    document.getElementById('totalSpecialists').textContent = visibleCount;
}

function openDemandModal(data) {
    currentSpecialistId = data.specialisteId;
    selectedTimeSlot = null;

    document.getElementById('specialisteIdInput').value = data.specialisteId;
    document.getElementById('specialisteNameDisplay').textContent = data.specialisteName;
    document.getElementById('specialiteTypeDisplay').textContent = data.specialiteType;
    document.getElementById('tarifDisplay').textContent = data.tarif + ' DH';
    document.getElementById('prixInput').value = data.tarif;
    document.getElementById('startDateInput').value = '';

    loadCalendrier(data.specialisteId);

    document.getElementById('demandModal').classList.remove('hidden');
}

function closeDemandModal() {
    document.getElementById('demandModal').classList.add('hidden');
    document.getElementById('demandForm').reset();
    currentSpecialistId = null;
    selectedTimeSlot = null;

    if (typeof currentWeekOffset !== 'undefined') {
        currentWeekOffset = 0;
    }
}

function loadCalendrier(specialisteId) {
    try {
        const calendrierDataFromMap = calendriersMap[specialisteId] || [];
        window.calendrierData = calendrierDataFromMap;

        window.role = 'generalist';

        if (typeof window.currentWeekOffset !== 'undefined') {
            window.currentWeekOffset = 0;
        }

        setTimeout(() => {
            if (typeof renderWeekCalendar === 'function') {
                renderWeekCalendar();
            } else {
                console.error('La fonction renderWeekCalendar n\'est pas définie');
            }
        }, 100);
    } catch (error) {
        console.error('Erreur lors du chargement du calendrier:', error);
    }
}

function selectTimeSlot(slotDate, slotTime, slotDiv) {
    if (selectedTimeSlot && selectedTimeSlot.element) {
        selectedTimeSlot.element.classList.add('bg-green-400', 'hover:bg-green-500');
        selectedTimeSlot.element.classList.remove('bg-orange-300');
    }

    const [hours, minutes] = slotTime.split(':').map(Number);
    const appointmentDate = new Date(
        slotDate.getFullYear(),
        slotDate.getMonth(),
        slotDate.getDate(),
        hours,
        minutes
    );

    const isoDate = appointmentDate.toISOString();

    document.getElementById('startDateInput').value = isoDate;

    selectedTimeSlot = {
        date: slotDate,
        time: slotTime,
        element: slotDiv,
        fullDate: appointmentDate
    };

    if (slotDiv) {
        slotDiv.classList.add('bg-orange-300');
        slotDiv.classList.remove('bg-green-400', 'hover:bg-green-500');
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById('demandModal');
    if (modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === modal) {
                closeDemandModal();
            }
        });
    }
});