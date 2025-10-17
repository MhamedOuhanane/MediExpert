<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mediexpert.model.Demande" %>
<%@ page import="com.mediexpert.model.Consultation" %>
<%@ page import="com.mediexpert.enums.DemandeStatut" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    List<Demande> demands = (List<Demande>) request.getAttribute("demands");
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime now = LocalDateTime.now();
%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="${pageContext.request.contextPath}/partials/_head.jsp" />
<body class="bg-gradient-to-br from-blue-50 via-purple-50 to-blue-100 min-h-screen">

    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <%@ include file="_sidebar.jsp" %>

        <main class="flex-1 overflow-y-auto p-3">
            <!-- Section: Liste des Demandes -->
            <div id="demands-list-section">
                <div class="bg-white/90 backdrop-blur-sm rounded-2xl shadow-xl p-4">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-800 flex items-center">
                            <svg class="w-7 h-7 mr-2 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                            Liste des Demandes d'Avis
                        </h3>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-sm">
                            <thead class="bg-gradient-to-r from-purple-50 to-blue-50">
                                <tr>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Patient</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Question</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Date Début</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Statut</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-700 uppercase">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                                <%
                                if (demands != null && !demands.isEmpty()) {
                                    for (Demande demande : demands) {
                                        Consultation consultation = demande.getConsultation();
                                        String patientName = consultation.getRecord().getPrenom() + " " + consultation.getRecord().getNom();
                                        String initials = consultation.getRecord().getPrenom().substring(0, 1).toUpperCase() +
                                                        consultation.getRecord().getNom().substring(0, 1).toUpperCase();

                                        double prixTotal = consultation.getPrix();
                                        String statusColor = "";
                                        String statusLabel = "";
                                        boolean canRespond = now.isAfter(demande.getStartDate());

                                        switch (demande.getStatut()) {
                                            case EN_ATTENTE_AVIS_SPECIALISTE:
                                                statusColor = "bg-orange-100 text-orange-800 border-orange-300";
                                                statusLabel = "En Attente";
                                                break;
                                            case TERMINEE:
                                                statusColor = "bg-green-100 text-green-800 border-green-300";
                                                statusLabel = "Terminée";
                                                break;
                                            case ANNULEE:
                                                statusColor = "bg-red-100 text-red-800 border-red-300";
                                                statusLabel = "Annulée";
                                                break;
                                            default:
                                                statusColor = "bg-gray-100 text-gray-800 border-gray-300";
                                                statusLabel = demande.getStatut().toString();
                                        }
                                %>
                                <tr class="hover:bg-gradient-to-r hover:from-purple-50 hover:to-blue-50 transition duration-200">
                                    <!-- Patient -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex items-center">
                                            <div class="w-9 h-9 rounded-full bg-gradient-to-br from-purple-400 to-blue-500 flex items-center justify-center text-white font-bold text-xs shadow-md">
                                                <%= initials %>
                                            </div>
                                            <div class="ml-3">
                                                <div class="text-sm font-bold text-gray-900">
                                                    <%= patientName %>
                                                </div>
                                                <div class="text-xs text-gray-500">
                                                    <%= consultation.getRecord().getCarte() %>
                                                </div>
                                            </div>
                                        </div>
                                    </td>

                                    <!-- Question -->
                                    <td class="px-4 py-3">
                                        <div class="text-sm text-gray-900 max-w-xs truncate">
                                            <%= demande.getQuestion() %>
                                        </div>
                                    </td>

                                    <!-- Date Début -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="text-xs text-gray-600">
                                            <%= demande.getStartDate().format(dateTimeFormatter) %>
                                        </div>
                                        <% if (!canRespond && demande.getStatut() == DemandeStatut.EN_ATTENTE_AVIS_SPECIALISTE) { %>
                                        <div class="text-xs text-orange-600 font-semibold mt-1">
                                            ⏳ Pas encore disponible
                                        </div>
                                        <% } else if (canRespond && demande.getStatut() == DemandeStatut.EN_ATTENTE_AVIS_SPECIALISTE) { %>
                                        <div class="text-xs text-green-600 font-semibold mt-1">
                                            ✓ Peut répondre
                                        </div>
                                        <% } %>
                                    </td>

                                    <!-- Statut -->
                                    <td class="px-4 py-3 whitespace-nowrap text-center">
                                        <span class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full border <%= statusColor %>">
                                            <%= statusLabel %>
                                        </span>
                                    </td>

                                    <!-- Actions -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex justify-center space-x-1.5">
                                            <!-- Voir détails consultation -->
                                            <button type="button" onclick='showConsultationDetails(<%=
                                                "{" +
                                                "\"id\":\"" + consultation.getId() + "\"," +
                                                "\"patientNom\":\"" + consultation.getRecord().getNom() + "\"," +
                                                "\"patientPrenom\":\"" + consultation.getRecord().getPrenom() + "\"," +
                                                "\"patientCarte\":\"" + consultation.getRecord().getCarte() + "\"," +
                                                "\"initials\":\"" + initials + "\"," +
                                                "\"raison\":\"" + consultation.getRaison().replace("\"", "\\\"") + "\"," +
                                                "\"observations\":\"" + consultation.getObservations().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"prix\":\"" + String.format("%.2f", prixTotal) + "\"," +
                                                "\"createdAt\":\"" + consultation.getCreatedAt().format(dateTimeFormatter) + "\"" +
                                                "}"
                                            %>)'
                                                    class="p-1.5 text-blue-600 hover:bg-blue-100 rounded-lg transition"
                                                    title="Voir consultation">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                                                </svg>
                                            </button>

                                            <!-- Répondre (si date disponible et statut EN_ATTENTE) -->
                                            <% if (canRespond && demande.getStatut() == DemandeStatut.EN_ATTENTE_AVIS_SPECIALISTE) { %>
                                            <button type="button" onclick='openResponseModal(<%=
                                                "{" +
                                                "\"demandeId\":\"" + demande.getId() + "\"," +
                                                "\"question\":\"" + demande.getQuestion().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"patientName\":\"" + patientName + "\"," +
                                                "\"currentResponse\":\"" + (demande.getResponse() != null ? demande.getResponse().replace("\"", "\\\"").replace("\n", "\\n") : "") + "\"" +
                                                "}"
                                            %>)'
                                                    class="p-1.5 text-green-600 hover:bg-green-100 rounded-lg transition"
                                                    title="Répondre">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 5H6a2 2 0 00-2 2v11a2 2 0 002 2h11a2 2 0 002-2v-5m-1.414-9.414a2 2 0 112.828 2.828L11.828 15H9v-2.828l8.586-8.586z"/>
                                                </svg>
                                            </button>
                                            <% } else if (consultation != null && consultation.getDemande() != null) {
                                                String responseText = consultation.getDemande().getResponse() != null ? consultation.getDemande().getResponse() : "";
                                                String demandId = consultation.getDemande().getId() != null ? consultation.getDemande().getId().toString() : null;
                                                String demandDate = consultation.getDemande().getStartDate() != null
                                                                            ? consultation.getDemande().getStartDate().format(dateTimeFormatter1)
                                                                            : null;
                                            %>
                                            <button type="button" onclick='viewResponse(<%=
                                                "{" +
                                                "\"question\":\"" + consultation.getDemande().getQuestion().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"response\":\"" + responseText.replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"patientCarte\":\"" + consultation.getRecord().getCarte().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"status\":\"" + consultation.getDemande().getStatut().toString().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"demandDate\":\"" + demandDate.replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"patientName\":\"" + consultation.getRecord().getNom()  + "\"" +
                                                "}"
                                            %>)'
                                                    class="p-1.5 text-purple-600 hover:bg-purple-100 rounded-lg transition"
                                                    title="Voir réponse">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                                                </svg>
                                            </button>
                                            <% } %>
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
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                                        </svg>
                                        Aucune demande trouvée
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <% if (demands != null && !demands.isEmpty()) { %>
                    <div class="flex items-center justify-between mt-4 pt-3 border-t border-gray-200">
                        <div class="text-xs text-gray-600">
                            Total: <span class="font-bold"><%= demands.size() %></span> demande(s)
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </main>
    </div>

    <!-- Modal Détails Consultation -->
    <div id="consultationDetailsModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-3xl max-h-[90vh] overflow-hidden transform transition-all animate-scale-in flex flex-col">
            <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white p-4">
                <div class="flex items-center justify-between">
                    <div class="flex items-center min-w-0 flex-1">
                        <div id="modalAvatar" class="w-10 h-10 rounded-full bg-white/20 backdrop-blur-sm flex items-center justify-center text-white font-bold text-sm shadow-lg mr-3 flex-shrink-0"></div>
                        <div class="min-w-0 flex-1">
                            <h3 class="text-lg font-bold truncate" id="modalPatientName"></h3>
                            <p class="text-xs text-white/80 truncate" id="modalPatientCarte"></p>
                        </div>
                    </div>
                    <button onclick="closeConsultationModal()" class="text-white/80 hover:text-white transition ml-3 flex-shrink-0">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <div class="flex-1 overflow-y-auto p-4 space-y-4">
                <div>
                    <div class="flex items-center mb-3">
                        <div class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center mr-2 flex-shrink-0">
                            <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                            </svg>
                        </div>
                        <h4 class="text-base font-bold text-gray-800">Détails de la Consultation</h4>
                    </div>
                    <div class="grid grid-cols-1 gap-3">
                        <div class="bg-blue-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Raison</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalRaison"></p>
                        </div>
                        <div class="bg-blue-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Observations</p>
                            <p class="text-sm text-gray-800 whitespace-pre-wrap" id="modalObservations"></p>
                        </div>
                        <div class="bg-blue-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Prix de la consultation</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalPrix"></span> DH</p>
                        </div>
                        <div class="bg-blue-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Date de création</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalCreatedAt"></p>
                        </div>
                    </div>
                </div>
            </div>

            <div class="bg-gray-50 p-3 border-t">
                <button onclick="closeConsultationModal()"
                        class="w-full px-4 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                    Fermer
                </button>
            </div>
        </div>
    </div>

    <!-- Modal Réponse -->
    <div id="responseModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-2xl max-h-[90vh] overflow-hidden transform transition-all animate-scale-in flex flex-col">
            <div class="bg-gradient-to-r from-blue-600 to-purple-600 text-white p-4">
                <div class="flex items-center justify-between">
                    <h3 class="text-lg font-bold">Répondre à la demande</h3>
                    <button onclick="closeResponseModal()" class="text-white/80 hover:text-white transition">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <form action="${pageContext.request.contextPath}/demandes/respond" method="POST" class="flex-1 overflow-y-auto p-4 space-y-4">
                <input type="hidden" name="demandeId" id="responseDemandeId">
                <input type="hidden" name="csrfToken" value="${csrfToken}">
                <input type="hidden" name="_methode" value="PUT">

                <div>
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Patient</label>
                    <p class="text-sm text-gray-600 bg-gray-50 p-2 rounded-lg" id="responsePatientName"></p>
                </div>

                <div>
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Question du généraliste</label>
                    <div class="bg-blue-50 p-3 rounded-lg border border-blue-200">
                        <p class="text-sm text-gray-800 whitespace-pre-wrap" id="responseQuestion"></p>
                    </div>
                </div>

                <div>
                    <label for="responseText" class="text-sm font-semibold text-gray-700 mb-1 block">Votre réponse *</label>
                    <textarea
                        id="responseText"
                        name="response"
                        rows="8"
                        required
                        class="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-transparent resize-none"
                        placeholder="Saisissez votre avis médical détaillé..."></textarea>
                </div>

                <div class="bg-yellow-50 border-l-4 border-yellow-400 p-3 rounded">
                    <div class="flex">
                        <svg class="w-5 h-5 text-yellow-600 mr-2 flex-shrink-0" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                        </svg>
                        <p class="text-xs text-yellow-800">
                            <strong>Important :</strong> Une fois votre réponse soumise, la demande sera marquée comme terminée et le généraliste sera notifié.
                        </p>
                    </div>
                </div>

                <div class="flex gap-3 pt-3 border-t">
                    <button type="button" onclick="closeResponseModal()"
                            class="flex-1 px-4 py-2 bg-gray-200 text-gray-700 font-semibold rounded-lg hover:bg-gray-300 transition text-sm">
                        Annuler
                    </button>
                    <button type="submit"
                            class="flex-1 px-4 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                        Envoyer la réponse
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Modal Voir Réponse (pour demandes terminées) -->
    <div id="viewResponseModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-2xl max-h-[90vh] overflow-hidden transform transition-all animate-scale-in flex flex-col">
            <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white p-4">
                <div class="flex items-center justify-between">
                    <h3 class="text-lg font-bold">Réponse envoyée</h3>
                    <button onclick="closeViewResponseModal()" class="text-white/80 hover:text-white transition">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <div class="flex-1 overflow-y-auto p-4 space-y-4">
                <div>
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Patient</label>
                    <div class="text-gray-600 bg-gray-50 p-2 rounded-lg">
                        <p class="text-sm" id="viewPatientName"></p>
                        <p class="text-xs" id="viewPatientCarte"></p>
                    </div>
                </div>

                <div>
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Question du généraliste</label>
                    <div class="bg-blue-50 p-3 rounded-lg border border-blue-200">
                        <p class="text-sm text-gray-800 whitespace-pre-wrap" id="viewQuestion"></p>
                    </div>
                </div>

                <div>
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Votre réponse</label>
                    <div class="bg-green-50 p-3 rounded-lg border border-green-200">
                        <p class="text-sm text-gray-800 whitespace-pre-wrap" id="viewResponse"></p>
                    </div>
                </div>

                <!-- Status Demande -->
                <div class="flex justify-between items-contre px-2">
                    <div>
                        <label class="text-sm font-semibold text-gray-700 mb-1 block">Demande</label>
                        <div class="flex flex-col space-y-2">
                            <span>Status: <p id="viewDemandeStatus" class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full border "></p></span>
                            <span>Start Date: <p id="viewDemandeDate" class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-gray=-100 text-gray-600 border "></p></span>
                        </div>
                    </div>
                </div>
            </div>

            <div class="bg-gray-50 p-3 border-t">
                <button onclick="closeViewResponseModal()"
                        class="w-full px-4 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                    Fermer
                </button>
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
    <script>
        let role = 'specialist';
    </script>
    <script src="${pageContext.request.contextPath}/js/demandes.js"></script>
</body>
</html>