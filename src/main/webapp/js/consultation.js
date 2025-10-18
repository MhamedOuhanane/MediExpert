function showConsultationDetails(consultation) {
    console.log('Consultation data:', consultation); // Debug

    var initials = consultation.initials || '';
    var patientName = (consultation.patientPrenom || '') + ' ' + (consultation.patientNom || '');

    document.getElementById('modalAvatar').textContent = initials;
    document.getElementById('modalPatientName').textContent = patientName;
    document.getElementById('modalPatientCarte').textContent = 'N° Carte: ' + (consultation.patientCarte || '');
    document.getElementById('modalRaison').textContent = consultation.raison || '';
    document.getElementById('modalObservations').textContent = consultation.observations || '';
    document.getElementById('modalActesTechniques').textContent = consultation.actesTechniques || 'Aucun acte';
    document.getElementById('modalPrix').textContent = consultation.prix || '0.00';
    document.getElementById('modalTotalActes').textContent = consultation.totalActes || '0.00';
    document.getElementById('modalPrixTotal').textContent = consultation.prixTotal || '0.00';
    document.getElementById('modalCreatedAt').textContent = consultation.createdAt || '';
    document.getElementById('modalUpdatedAt').textContent = consultation.updatedAt || '';

    var statusElement = document.getElementById('modalStatus');
    statusElement.textContent = consultation.status || '';
    statusElement.className = 'px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full border ' + (consultation.statusColor || 'bg-gray-100 text-gray-800');

    document.getElementById('consultationDetailsModal').classList.remove('hidden');
}

function closeConsultationDetailsModal() {
    document.getElementById('consultationDetailsModal').classList.add('hidden');
}

function showNotification(message, type) {
    var bgColor = type === 'success' ? 'bg-green-500' : 'bg-red-500';
    var notification = document.createElement('div');
    notification.className = 'fixed top-4 right-4 ' + bgColor + ' text-white px-6 py-3 rounded-lg shadow-lg z-50';
    notification.textContent = message;
    document.body.appendChild(notification);

    setTimeout(function() {
        notification.style.opacity = '0';
        notification.style.transition = 'opacity 0.3s';
        setTimeout(function() {
            notification.remove();
        }, 300);
    }, 3000);
}

document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById('consultationDetailsModal');
    if (modal) {
        modal.addEventListener('click', function(e) {
            if (e.target === this) {
                closeConsultationDetailsModal();
            }
        });
    }

    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' || e.key === 'Esc') {
            closeConsultationDetailsModal();
        }
    });

    var urlParams = new URLSearchParams(window.location.search);
    var message = urlParams.get('message');
    var error = urlParams.get('error');

    if (message) {
        showNotification(decodeURIComponent(message), 'success');
    }

    if (error) {
        showNotification(decodeURIComponent(error), 'error');
    }
});