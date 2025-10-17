<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="fr">
<jsp:include page="${pageContext.request.contextPath}/partials/_head.jsp" />
<body class="bg-gradient-to-br from-blue-50 via-purple-50 to-blue-100 min-h-screen">

    <div class="flex h-screen overflow-hidden">

        <!-- Sidebar -->
        <%@ include file="_sidebar.jsp" %>

        <main class="flex-1 overflow-y-auto m-2">
                <div id="profile-section" class="content-section">
                    <div class="bg-white rounded-2xl shadow-xl overflow-hidden">
                        <!-- En-tête du profil avec image de fond -->
                        <div class="relative h-32 w-full overflow-hidden">
                            <!-- Image en arrière-plan -->
                            <img src="${pageContext.request.contextPath}/images/specialistBackg.jpg"
                                 alt="Logo de fond"
                                 class="w-full bg-contain" />
                        </div>

                        <div class="px-8 pb-8">
                            <!-- Photo de profil -->
                            <div class="flex items-end -mt-16 mb-6">
                                <div class="relative">
                                    <div class="w-32 h-32 rounded-full border-4 border-white shadow-xl bg-gradient-to-br from-purple-300 to-blue-400 flex items-center justify-center">
                                        <img src="${pageContext.request.contextPath}/images/specialistLogo.png"
                                             alt="Logo Speciliste"
                                             class="h-[9.3rem] object-cover" />
                                    </div>
                                </div>
                                <div class="ml-6">
                                    <h3 class="text-2xl font-bold text-gray-800">M. ${specialist.nom} ${specialist.prenom}</h3>
                                    <p class="text-gray-500">N° Carte: <span class="text-gray-800"> ${ specialist.carte } </span> </p>
                                </div>
                            </div>

                            <!-- Informations personnelles -->
                            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">

                                <!-- Carte d'information -->
                                <div class="bg-gradient-to-br from-blue-50 to-purple-50 rounded-xl p-6 border border-blue-100">
                                    <h4 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                                        <svg class="w-5 h-5 mr-2 text-blue-600" fill="currentColor" viewBox="0 0 24 24">
                                            <path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/>
                                        </svg>
                                        Informations Personnelles
                                    </h4>
                                    <div class="space-y-3">
                                        <div>
                                            <label class="text-sm font-medium text-gray-500">Email</label>
                                            <p class="text-gray-800 font-medium">${specialist.email}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-500">Téléphone</label>
                                            <p class="text-gray-800 font-medium">${specialist.telephone}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-500">Date de naissance</label>
                                            <p class="text-gray-800 font-medium">${specialist.dateNaissance}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-500">Rôle</label>
                                            <span class="inline-block px-3 py-1 bg-red-200 text-red-900 rounded-full text-sm font-medium">
                                                Speciliste
                                            </span>
                                        </div>
                                        <h4 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                                            <svg class="w-5 h-5 mr-2 text-blue-600" fill="currentColor" viewBox="0 0 24 24">
                                                <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                                            </svg>
                                            Informations Système
                                        </h4>
                                        <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                            <div>
                                                <label class="text-sm font-medium text-gray-500">Date de création</label>
                                                <p class="text-gray-800 font-medium">${specialist.getCreatedAtFormatted()}</p>
                                            </div>
                                            <div>
                                                <label class="text-sm font-medium text-gray-500">Dernière mise à jour</label>
                                                <p class="text-gray-800 font-medium">${specialist.getUpdatedAtFormatted()}</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <!-- Calendrier -->
                                <%@ include file="../../partials/_calendrier.jsp" %>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </main>
    </div>

    <!-- Modal pour ajouter un calendrier -->
    <div id="calendrierModal" class="hidden fixed inset-0 bg-black bg-opacity-50 items-center justify-center z-50">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-md mx-4 overflow-hidden">
            <!-- En-tête du modal -->
            <div class="bg-gradient-to-r from-purple-600 to-blue-600 px-6 py-4 flex justify-between items-center">
                <h3 class="text-xl font-bold text-white flex items-center">
                    <svg class="w-6 h-6 mr-2" fill="currentColor" viewBox="0 0 24 24">
                        <path d="M19 4h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V10h14v10zM5 8V6h14v2H5zm2 4h10v2H7v-2z"/>
                    </svg>
                    Ajouter un Jour Travail
                </h3>
                <button onclick="closeCalendrierModal()" class="text-white hover:text-gray-200 transition">
                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                    </svg>
                </button>
            </div>

            <!-- Corps du modal -->
            <form id="calendrierForm" action="${pageContext.request.contextPath}/calendriers" method="POST" class="p-6">
                <input type="hidden" id="calendrierDate" name="date" />
                <input type="hidden" name="specialiste_id" value="${specialist.id}" />
                <input type="hidden" name="csrfToken" value="${csrfToken}" />

                <div class="mb-6 bg-blue-50 border border-blue-200 rounded-lg p-4">
                    <label class="text-sm font-medium text-blue-800 mb-1 block">Date sélectionnée</label>
                    <p id="displayDate" class="text-lg font-bold text-blue-900"></p>
                </div>

                <!-- Heure de début -->
                <div class="mb-4">
                    <label for="startTime" class="block text-sm font-medium text-gray-700 mb-2">
                        <svg class="w-4 h-4 inline mr-1 text-green-600" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                        </svg>
                        Heure de début
                    </label>
                    <input
                        type="time"
                        id="startTime"
                        name="startTime"
                        required
                        class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
                        value="08:00"
                    />
                </div>

                <!-- Heure de fin -->
                <div class="mb-6">
                    <label for="endTime" class="block text-sm font-medium text-gray-700 mb-2">
                        <svg class="w-4 h-4 inline mr-1 text-red-600" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                        </svg>
                        Heure de fin
                    </label>
                    <input
                        type="time"
                        id="endTime"
                        name="endTime"
                        required
                        class="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-purple-500 focus:border-transparent transition"
                        value="18:00"
                    />
                </div>

                <!-- Boutons d'action -->
                <div class="flex gap-3">
                    <button
                        type="button"
                        onclick="closeCalendrierModal()"
                        class="flex-1 px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300 transition font-medium"
                    >
                        Annuler
                    </button>
                    <button
                        type="submit"
                        class="flex-1 px-4 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white rounded-lg hover:from-purple-700 hover:to-blue-700 transition font-medium shadow-lg"
                    >
                        Ajouter
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        let calendrierData = ${calendrierJson} || [];
        const role = 'specialist';

        // Mettre à jour l'affichage de la date quand le modal s'ouvre
        document.getElementById('calendrierModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeCalendrierModal();
            }
        });

        // Observer les changements sur le champ date caché
        const dateInput = document.getElementById('calendrierDate');
        const displayDate = document.getElementById('displayDate');

        if (dateInput && displayDate) {
            const observer = new MutationObserver(function() {
                const dateValue = dateInput.value;
                if (dateValue) {
                    const [year, month, day] = dateValue.split('-');
                    const date = new Date(year, month - 1, day);
                    displayDate.textContent = new Intl.DateTimeFormat('fr-FR', {
                        weekday: 'long',
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric'
                    }).format(date);
                }
            });

            observer.observe(dateInput, { attributes: true, attributeFilter: ['value'] });

            // Déclencher aussi sur changement direct
            dateInput.addEventListener('change', function() {
                const dateValue = this.value;
                if (dateValue) {
                    const [year, month, day] = dateValue.split('-');
                    const date = new Date(year, month - 1, day);
                    displayDate.textContent = new Intl.DateTimeFormat('fr-FR', {
                        weekday: 'long',
                        year: 'numeric',
                        month: 'long',
                        day: 'numeric'
                    }).format(date);
                }
            });
        }
    </script>

</body>
</html>