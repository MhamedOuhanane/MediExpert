<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mediexpert.model.Record" %>
<%@ page import="com.mediexpert.enums.StatusPatient" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    List<Record> patients = (List<Record>) request.getAttribute("patients");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="${pageContext.request.contextPath}/partials/_head.jsp" />
<body class="bg-gradient-to-br from-blue-50 via-purple-50 to-blue-100 min-h-screen">

    <div class="flex h-screen overflow-hidden">

        <!-- Sidebar -->
        <%@ include file="_sidebar.jsp" %>

        <main class="flex-1 overflow-y-auto p-3">
            <!-- Section: Liste des Patients -->
            <div id="patients-list-section">
                <div class="bg-white/90 backdrop-blur-sm rounded-2xl shadow-xl p-4">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-800 flex items-center">
                            <svg class="w-7 h-7 mr-2 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                            </svg>
                            Liste des Patients
                        </h3>
                        <a href="${pageContext.request.contextPath}/patients/add"
                           class="px-4 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white text-sm font-semibold rounded-lg shadow-md hover:shadow-lg transition-all">
                            + Nouveau
                        </a>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-sm">
                            <thead class="bg-gradient-to-r from-blue-50 to-purple-50">
                                <tr>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Nom Prénom</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">N° Carte</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Modifié</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Statut</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-700 uppercase">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                                <%
                                if (patients != null && !patients.isEmpty()) {
                                    for (Record patient : patients) {
                                        String initials = patient.getPrenom().substring(0, 1).toUpperCase() +
                                                        patient.getNom().substring(0, 1).toUpperCase();
                                        String statusColor = "";
                                        String statusLabel = "";

                                        switch (patient.getStatus()) {
                                            case EN_ATTENTE:
                                                statusColor = "bg-yellow-100 text-yellow-800 border-yellow-300";
                                                statusLabel = "En Attente";
                                                break;
                                            case EN_COURS:
                                                statusColor = "bg-blue-100 text-blue-800 border-blue-300";
                                                statusLabel = "En Cours";
                                                break;
                                            case TERMINEE:
                                                statusColor = "bg-green-100 text-green-800 border-green-300";
                                                statusLabel = "Terminé";
                                                break;
                                            case ANNULEE:
                                                statusColor = "bg-red-100 text-red-800 border-red-300";
                                                statusLabel = "Annulé";
                                                break;
                                            default:
                                                statusColor = "bg-gray-100 text-gray-800 border-gray-300";
                                                statusLabel = patient.getStatus().toString();
                                        }
                                %>
                                <tr class="hover:bg-gradient-to-r hover:from-blue-50 hover:to-purple-50 transition duration-200">
                                    <!-- Nom Prénom -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex items-center">
                                            <div class="w-9 h-9 rounded-full bg-gradient-to-br from-blue-400 to-purple-500 flex items-center justify-center text-white font-bold text-xs shadow-md">
                                                <%= initials %>
                                            </div>
                                            <div class="ml-3">
                                                <div class="text-sm font-bold text-gray-900">
                                                    <%= patient.getPrenom() %> <%= patient.getNom() %>
                                                </div>
                                            </div>
                                        </div>
                                    </td>

                                    <!-- N° Carte -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <span class="text-sm font-mono text-gray-700"><%= patient.getCarte() %></span>
                                    </td>

                                    <!-- Date Modification -->
                                    <td class="px-4 py-3 whitespace-nowrap text-xs text-gray-600">
                                        <%= patient.getUpdatedAt().format(dateTimeFormatter) %>
                                    </td>

                                    <!-- Statut -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <span class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full border <%= statusColor %>">
                                            <%= statusLabel %>
                                        </span>
                                    </td>

                                    <!-- Actions -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex justify-center space-x-1.5">
                                            <!-- Voir détails -->
                                            <button onclick='showPatientDetails(<%=
                                                "{" +
                                                "\"id\":\"" + patient.getId() + "\"," +
                                                "\"nom\":\"" + patient.getNom() + "\"," +
                                                "\"prenom\":\"" + patient.getPrenom() + "\"," +
                                                "\"dateNaissance\":\"" + patient.getDateNaissance().format(dateFormatter) + "\"," +
                                                "\"carte\":\"" + patient.getCarte() + "\"," +
                                                "\"telephone\":\"" + patient.getTelephone() + "\"," +
                                                "\"tension\":\"" + (patient.getTension() != null ? patient.getTension() : "-") + "\"," +
                                                "\"frequenceCardiaque\":\"" + (patient.getFrequenceCardiaque() != null ? patient.getFrequenceCardiaque() : "-") + "\"," +
                                                "\"temperature\":\"" + (patient.getTemperature() != null ? patient.getTemperature() : "-") + "\"," +
                                                "\"frequenceRespiratoire\":\"" + (patient.getFrequenceRespiratoire() != null ? patient.getFrequenceRespiratoire() : "-") + "\"," +
                                                "\"poids\":\"" + (patient.getPoids() != null ? patient.getPoids() : "-") + "\"," +
                                                "\"taille\":\"" + (patient.getTaille() != null ? patient.getTaille() : "-") + "\"," +
                                                "\"status\":\"" + statusLabel + "\"," +
                                                "\"statusColor\":\"" + statusColor + "\"," +
                                                "\"updatedAt\":\"" + patient.getUpdatedAt().format(dateTimeFormatter) + "\"" +
                                                "}"
                                            %>)'
                                                    class="p-1.5 text-blue-600 hover:bg-blue-100 rounded-lg transition"
                                                    title="Voir détails">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                                                </svg>
                                            </button>

                                            <!-- Annuler -->
                                            <% if (patient.getStatus().equals(StatusPatient.EN_ATTENTE)) { %>
                                            <button onclick="showCancelModal('<%= patient.getId() %>', '<%= patient.getPrenom() %> <%= patient.getNom() %>')"
                                                    class="p-1.5 text-orange-600 hover:bg-orange-100 rounded-lg transition"
                                                    title="Annuler">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                                                </svg>
                                            </button>
                                            <% } %>

                                            <!-- Supprimer -->
                                            <button onclick="showDeleteModal('<%= patient.getId() %>', '<%= patient.getPrenom() %> <%= patient.getNom() %>')"
                                                    class="p-1.5 text-red-600 hover:bg-red-100 rounded-lg transition"
                                                    title="Supprimer">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                                                </svg>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                                <%
                                    }
                                } else {
                                %>
                                <tr>
                                    <td colspan="5" class="px-4 py-8 text-center text-gray-500">
                                        <svg class="w-12 h-12 mx-auto mb-3 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"/>
                                        </svg>
                                        Aucun patient trouvé
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination -->
                    <% if (patients != null && !patients.isEmpty()) { %>
                    <div class="flex items-center justify-between mt-4 pt-3 border-t border-gray-200">
                        <div class="text-xs text-gray-600">
                            Total: <span class="font-bold"><%= patients.size() %></span> patient(s)
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </main>
    </div>

    <!-- Modal Détails Patient -->
    <div id="detailsModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-2xl max-h-[90vh] overflow-y-auto transform transition-all animate-scale-in">
            <!-- Header -->
            <div class="sticky top-0 bg-gradient-to-r from-blue-600 to-purple-600 text-white p-6 rounded-t-2xl">
                <div class="flex items-center justify-between">
                    <div class="flex items-center">
                        <div id="modalAvatar" class="w-12 h-12 rounded-full bg-white/20 backdrop-blur-sm flex items-center justify-center text-white font-bold text-lg shadow-lg mr-4">
                        </div>
                        <div>
                            <h3 class="text-2xl font-bold" id="modalPatientName"></h3>
                            <p class="text-sm text-white/80" id="modalCarte"></p>
                        </div>
                    </div>
                    <button onclick="closeDetailsModal()" class="text-white/80 hover:text-white transition">
                        <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- Body -->
            <div class="p-6 space-y-6">
                <!-- Informations Personnelles -->
                <div>
                    <div class="flex items-center mb-4">
                        <div class="w-10 h-10 bg-blue-100 rounded-lg flex items-center justify-center mr-3">
                            <svg class="w-5 h-5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                            </svg>
                        </div>
                        <h4 class="text-lg font-bold text-gray-800">Informations Personnelles</h4>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div class="bg-gray-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Date de Naissance</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalDateNaissance"></p>
                        </div>
                        <div class="bg-gray-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Téléphone</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalTelephone"></p>
                        </div>
                    </div>
                </div>

                <!-- Signes Vitaux -->
                <div>
                    <div class="flex items-center mb-4">
                        <div class="w-10 h-10 bg-green-100 rounded-lg flex items-center justify-center mr-3">
                            <svg class="w-5 h-5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"/>
                            </svg>
                        </div>
                        <h4 class="text-lg font-bold text-gray-800">Signes Vitaux</h4>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div class="bg-green-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Tension</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalTension"></p>
                        </div>
                        <div class="bg-green-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Fréquence Cardiaque</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalFrequenceCardiaque"></span> bpm</p>
                        </div>
                        <div class="bg-green-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Température</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalTemperature"></span> °C</p>
                        </div>
                        <div class="bg-green-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Fréquence Respiratoire</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalFrequenceRespiratoire"></span> /min</p>
                        </div>
                    </div>
                </div>

                <!-- Mesures Physiques -->
                <div>
                    <div class="flex items-center mb-4">
                        <div class="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center mr-3">
                            <svg class="w-5 h-5 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3"/>
                            </svg>
                        </div>
                        <h4 class="text-lg font-bold text-gray-800">Mesures Physiques</h4>
                    </div>
                    <div class="grid grid-cols-2 gap-4">
                        <div class="bg-purple-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Poids</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalPoids"></span> kg</p>
                        </div>
                        <div class="bg-purple-50 p-3 rounded-lg">
                            <p class="text-xs text-gray-500 mb-1">Taille</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalTaille"></span> m</p>
                        </div>
                    </div>
                </div>

                <!-- Statut et Mise à jour -->
                <div class="border-t pt-4">
                    <div class="grid grid-cols-2 gap-4">
                        <div>
                            <p class="text-xs text-gray-500 mb-2">Statut</p>
                            <span id="modalStatus" class="px-3 py-1 inline-flex text-xs leading-5 font-semibold rounded-full"></span>
                        </div>
                        <div>
                            <p class="text-xs text-gray-500 mb-1">Dernière Modification</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalUpdatedAt"></p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <div class="sticky bottom-0 bg-gray-50 p-4 rounded-b-2xl">
                <button onclick="closeDetailsModal()"
                        class="w-full px-4 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition">
                    Fermer
                </button>
            </div>
        </div>
    </div>

    <!-- Modal Confirmation Annulation -->
    <div id="cancelModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-md transform transition-all animate-scale-in">
            <div class="p-6">
                <div class="flex items-center justify-center w-16 h-16 mx-auto mb-4 bg-orange-100 rounded-full">
                    <svg class="w-8 h-8 text-orange-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-gray-800 text-center mb-2">Annuler le Patient</h3>
                <p class="text-sm text-gray-600 text-center mb-6">
                    Êtes-vous sûr de vouloir annuler le patient <span id="cancelPatientName" class="font-bold"></span> ?
                </p>
                <form id="cancelForm" action="${pageContext.request.contextPath}/patients/status" method="POST">
                    <input type="hidden" name="_method" value="PUT">
                    <input type="hidden" name="status" value="ANNULEE">
                    <input type="hidden" name="id" id="cancelPatientId">
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <div class="flex space-x-3">
                        <button type="button" onclick="closeCancelModal()"
                                class="flex-1 px-4 py-2 border-2 border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-50 transition">
                            Non, garder
                        </button>
                        <button type="submit"
                                class="flex-1 px-4 py-2 bg-orange-600 text-white font-semibold rounded-lg shadow-md hover:bg-orange-700 transition">
                            Oui, annuler
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!-- Modal Confirmation Suppression -->
    <div id="deleteModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-md transform transition-all animate-scale-in">
            <div class="p-6">
                <div class="flex items-center justify-center w-16 h-16 mx-auto mb-4 bg-red-100 rounded-full">
                    <svg class="w-8 h-8 text-red-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"/>
                    </svg>
                </div>
                <h3 class="text-xl font-bold text-gray-800 text-center mb-2">Supprimer le Patient</h3>
                <p class="text-sm text-gray-600 text-center mb-6">
                    Êtes-vous sûr de vouloir supprimer définitivement le patient <span id="deletePatientName" class="font-bold"></span> ? Cette action est irréversible.
                </p>
                <form id="deleteForm" action="${pageContext.request.contextPath}/patients/delete" method="POST">
                    <input type="hidden" name="id" id="deletePatientId">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="csrfToken" value="${csrfToken}">
                    <div class="flex space-x-3">
                        <button type="button" onclick="closeDeleteModal()"
                                class="flex-1 px-4 py-2 border-2 border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-gray-50 transition">
                            Annuler
                        </button>
                        <button type="submit"
                                class="flex-1 px-4 py-2 bg-red-600 text-white font-semibold rounded-lg shadow-md hover:bg-red-700 transition">
                            Supprimer
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <style>
        @keyframes scale-in {
            from {
                opacity: 0;
                transform: scale(0.9);
            }
            to {
                opacity: 1;
                transform: scale(1);
            }
        }
        .animate-scale-in {
            animation: scale-in 0.2s ease-out;
        }
    </style>

    <!-- Charger le fichier JavaScript externe -->
    <script src="${pageContext.request.contextPath}/js/patient.js"></script>
</body>
</html>