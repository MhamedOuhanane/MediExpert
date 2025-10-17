function showConsultationDetails(consultation) {
    document.getElementById('modalAvatar').textContent = consultation.initials;
    document.getElementById('modalPatientName').textContent = consultation.patientPrenom + ' ' + consultation.patientNom;
    document.getElementById('modalPatientCarte').textContent = 'N° Carte: ' + consultation.patientCarte;
    document.getElementById('modalRaison').textContent = consultation.raison;
    document.getElementById('modalObservations').textContent = consultation.observations;
    document.getElementById('modalPrix').textContent = consultation.prix;
    document.getElementById('modalCreatedAt').textContent = consultation.createdAt;

    document.getElementById('consultationDetailsModal').classList.remove('hidden');
}

function closeConsultationModal() {
    document.getElementById('consultationDetailsModal').classList.add('hidden');
}

function openResponseModal(data) {
    document.getElementById('responseDemandeId').value = data.demandeId;
    document.getElementById('responsePatientName').textContent = data.patientName;
    document.getElementById('responseQuestion').textContent = data.question;
    document.getElementById('responseText').value = data.currentResponse || '';

    document.getElementById('responseModal').classList.remove('hidden');
    document.getElementById('responseText').focus();
}

function closeResponseModal() {
    document.getElementById('responseModal').classList.add('hidden');
    document.getElementById('responseText').value = '';
}

function viewResponse(data) {
    document.getElementById('viewPatientName').textContent = data.patientName;
    document.getElementById('viewQuestion').textContent = data.question;
    if (data.response !== '') document.getElementById('viewResponse').textContent = data.response;
    else if (role !== 'specialist') document.getElementById('viewResponseParent').classList.add('hidden');
    document.getElementById('viewPatientCarte').textContent = data.patientCarte;
    document.getElementById('viewDemandeStatus').innerText = data.status;
    if (data.demandDate) {
        document.getElementById('viewDemandeDate').innerText = 'Date: ' + data.demandDate ;
    }

    const cancelContainer = document.getElementById('cancelDemandeContainer');
    const cancelIdInput = document.getElementById('cancelDemandeId');
    if (role !== 'specialist') {
        if (data.status === 'EN_ATTENTE_AVIS_SPECIALISTE' && data.demandDate) {
            document.getElementById('viewDemandeStatus').classList.add('bg-blue-100', 'text-blue-800');
            const today = new Date();
            const demandDateObj = new Date(data.demandDate);
            console.log(demandDateObj > today, demandDateObj, data.demandDate, today)
            if (demandDateObj > today) {
                cancelContainer.classList.remove('hidden');
                cancelIdInput.value = data.demandId;
            } else {
                cancelContainer.classList.add('hidden');
            }
        } else if (data.status === 'TERMINEE') {
            cancelContainer.classList.add('hidden');
            document.getElementById('viewDemandeStatus').classList.add('bg-green-100', 'text-green-800');
        } else {
            cancelContainer.classList.add('hidden');
        }
    }
    document.getElementById('viewResponseModal').classList.remove('hidden');
}

function closeViewResponseModal() {
    document.getElementById('viewResponseModal').classList.add('hidden');
}

document.addEventListener('keydown', function(event) {
    if (event.key === 'Escape') {
        closeConsultationModal();
        closeResponseModal();
        closeViewResponseModal();
    }
});

document.getElementById('consultationDetailsModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeConsultationModal();
    }
});

document.getElementById('responseModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeResponseModal();
    }
});

document.getElementById('viewResponseModal')?.addEventListener('click', function(e) {
    if (e.target === this) {
        closeViewResponseModal();
    }
});