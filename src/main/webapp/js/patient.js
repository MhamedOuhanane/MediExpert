function showPatientDetails(patient) {
    const initials = patient.prenom.substring(0, 1) + patient.nom.substring(0, 1);
    document.getElementById('modalAvatar').textContent = initials.toUpperCase();
    document.getElementById('modalPatientName').textContent = `${patient.prenom} ${patient.nom}`;
    document.getElementById('modalCarte').textContent = `N° Sécu: ${patient.carte}`;
    document.getElementById('modalDateNaissance').textContent = patient.dateNaissance;
    document.getElementById('modalTelephone').textContent = patient.telephone;
    document.getElementById('modalTension').textContent = patient.tension;
    document.getElementById('modalFrequenceCardiaque').textContent = patient.frequenceCardiaque;
    document.getElementById('modalTemperature').textContent = patient.temperature;
    document.getElementById('modalFrequenceRespiratoire').textContent = patient.frequenceRespiratoire;
    document.getElementById('modalPoids').textContent = patient.poids;
    document.getElementById('modalTaille').textContent = patient.taille;
    const statusElement = document.getElementById('modalStatus');
    statusElement.textContent = patient.status;
    statusElement.className = 'px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full border ' + (patient.statusColor || '');
    document.getElementById('modalUpdatedAt').textContent = patient.updatedAt;
    document.getElementById('detailsModal').classList.remove('hidden');
}
function closeDetailsModal() {
    document.getElementById('detailsModal').classList.add('hidden');
}
function showCancelModal(id, name) {
    document.getElementById('cancelPatientId').value = id;
    document.getElementById('cancelPatientName').textContent = name;
    document.getElementById('cancelModal').classList.remove('hidden');
}
function closeCancelModal() {
    document.getElementById('cancelModal').classList.add('hidden');
}
function showConsultationModal(recordId, patientName) {
    document.getElementById('consultationRecordId').value = recordId;
    document.getElementById('consultationPatientName').textContent = patientName;
    document.getElementById('consultationModal').classList.remove('hidden');
}
function closeConsultationModal() {
    document.getElementById('consultationModal').classList.add('hidden');
    const form = document.getElementById('consultationForm');
    if (form) form.reset();
}
function showNotification(message, type) {
    const bgColor = type === 'success' ? 'bg-green-500' : 'bg-red-500';
    const notification = document.createElement('div');
    notification.className = `fixed top-4 right-4 ${bgColor} text-white px-6 py-3 rounded-lg shadow-lg z-50 animate-slide-in`;
    notification.textContent = message;
    document.body.appendChild(notification);
    setTimeout(() => {
        notification.style.opacity = '0';
        notification.style.transition = 'opacity 0.3s';
        setTimeout(() => notification.remove(), 300);
    }, 3000);
}
document.addEventListener('DOMContentLoaded', function () {
    const modals = [
        { id: 'detailsModal', close: closeDetailsModal },
        { id: 'cancelModal', close: closeCancelModal },
        { id: 'deleteModal', close: closeDeleteModal },
        { id: 'consultationModal', close: closeConsultationModal },
    ];
    modals.forEach(({ id, close }) => {
        const modal = document.getElementById(id);
        if (modal) {
            modal.addEventListener('click', function (e) {
                if (e.target === this) close();
            });
        }
    });
    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            closeDetailsModal();
            closeCancelModal();
            closeDeleteModal();
            closeConsultationModal();
        }
    });
    const urlParams = new URLSearchParams(window.location.search);
    const message = urlParams.get('message');
    const error = urlParams.get('error');
    if (message) showNotification(message, 'success');
    if (error) showNotification(error, 'error');
});
