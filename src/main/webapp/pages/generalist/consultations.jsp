<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mediexpert.model.Consultation" %>
<%@ page import="com.mediexpert.model.ActesTechniques" %>
<%@ page import="com.mediexpert.enums.ConsultationStatut" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    List<Consultation> consultations = (List<Consultation>) request.getAttribute("consultations");
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
            <!-- Section: Liste des Consultations -->
            <div id="consultations-list-section">
                <div class="bg-white/90 backdrop-blur-sm rounded-2xl shadow-xl p-4">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-800 flex items-center">
                            <svg class="w-7 h-7 mr-2 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                            </svg>
                            Liste des Consultations
                        </h3>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-sm">
                            <thead class="bg-gradient-to-r from-green-50 to-teal-50">
                                <tr>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Patient</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Raison</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Date</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Prix</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Statut</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-700 uppercase">Actions</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200">
                                <%
                                if (consultations != null && !consultations.isEmpty()) {
                                    for (Consultation consultation : consultations) {
                                        String patientName = consultation.getRecord().getPrenom() + " " + consultation.getRecord().getNom();
                                        String initials = consultation.getRecord().getPrenom().substring(0, 1).toUpperCase() +
                                                        consultation.getRecord().getNom().substring(0, 1).toUpperCase();

                                        String statusColor = "";
                                        String statusLabel = "";

                                        switch (consultation.getStatut()) {
                                            case EN_ATTENTE_AVIS_SPECIALISTE:
                                                statusColor = "bg-blue-100 text-blue-800 border-blue-300";
                                                statusLabel = "En Attente Avis Specialiste";
                                                break;
                                            case TERMINEE:
                                                statusColor = "bg-green-100 text-green-800 border-green-300";
                                                statusLabel = "Terminée";
                                                break;
                                            default:
                                                statusColor = "bg-gray-100 text-gray-800 border-gray-300";
                                                statusLabel = consultation.getStatut().toString();
                                        }

                                        // Préparer la liste des actes techniques
                                        StringBuilder actesNoms = new StringBuilder();
                                        double totalActes = 0.0;
                                        if (consultation.getActesTechniques() != null && !consultation.getActesTechniques().isEmpty()) {
                                            for (int i = 0; i < consultation.getActesTechniques().size(); i++) {
                                                ActesTechniques acte = consultation.getActesTechniques().get(i);
                                                if (i > 0) actesNoms.append(", ");
                                                actesNoms.append(acte.getNom()).append(" (").append(acte.getPrix()).append(" DH)");
                                                totalActes += acte.getPrix();
                                            }
                                        } else {
                                            actesNoms.append("Aucun acte");
                                        }
                                %>
                                <tr class="hover:bg-gradient-to-r hover:from-green-50 hover:to-teal-50 transition duration-200">
                                    <!-- Patient -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex items-center">
                                            <div class="w-9 h-9 rounded-full bg-gradient-to-br from-green-400 to-teal-500 flex items-center justify-center text-white font-bold text-xs shadow-md">
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

                                    <!-- Raison -->
                                    <td class="px-4 py-3">
                                        <div class="text-sm text-gray-900 max-w-xs truncate">
                                            <%= consultation.getRaison() %>
                                        </div>
                                    </td>

                                    <!-- Date -->
                                    <td class="px-4 py-3 whitespace-nowrap text-xs text-gray-600">
                                        <%= consultation.getCreatedAt().format(dateTimeFormatter) %>
                                    </td>

                                    <!-- Prix -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <span class="text-sm font-semibold text-gray-900"><%= String.format("%.2f", consultation.getPrix() + totalActes) %> DH</span>
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
                                            <!-- Voir détails -->
                                            <button type="button" onclick='showConsultationDetails(<%=
                                                "{" +
                                                "\"id\":\"" + consultation.getId() + "\"," +
                                                "\"patientNom\":\"" + consultation.getRecord().getNom() + "\"," +
                                                "\"patientPrenom\":\"" + consultation.getRecord().getPrenom() + "\"," +
                                                "\"patientCarte\":\"" + consultation.getRecord().getCarte() + "\"," +
                                                "\"initials\":\"" + initials + "\"," +
                                                "\"raison\":\"" + consultation.getRaison().replace("\"", "\\\"") + "\"," +
                                                "\"observations\":\"" + consultation.getObservations().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"prix\":\"" + String.format("%.2f", consultation.getPrix()) + "\"," +
                                                "\"totalActes\":\"" + String.format("%.2f", totalActes) + "\"," +
                                                "\"prixTotal\":\"" + String.format("%.2f", consultation.getPrix() + totalActes) + "\"," +
                                                "\"actesTechniques\":\"" + actesNoms.toString().replace("\"", "\\\"") + "\"," +
                                                "\"status\":\"" + statusLabel + "\"," +
                                                "\"statusColor\":\"" + statusColor + "\"," +
                                                "\"createdAt\":\"" + consultation.getCreatedAt().format(dateTimeFormatter) + "\"," +
                                                "\"updatedAt\":\"" + consultation.getUpdatedAt().format(dateTimeFormatter) + "\"" +
                                                "}"
                                            %>)'
                                                    class="p-1.5 text-green-600 hover:bg-green-100 rounded-lg transition"
                                                    title="Voir détails">
                                                <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"/>
                                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"/>
                                                </svg>
                                            </button>

                                            <!-- Faire demand -->
                                            <% if (consultation != null && consultation.getStatut() == ConsultationStatut.EN_ATTENTE_AVIS_SPECIALISTE) { %>
                                            <form action="${pageContext.request.contextPath}/demandes/demand" method="POST" class="inline">
                                                <input type="hidden" name="consultationId" value="<%= consultation.getId() %>" />
                                                <input type="hidden" name="csrfToken" value="${csrfToken}" />
                                                <button type="submit"
                                                        class="p-1.5 text-blue-500 hover:bg-blue-100 rounded-lg transition"
                                                        title="Demander">
                                                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                                                        <path d="M4 4h10v2H6v12h12V8h2v10a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6c0-1.1.9-2 2-2zM20 2h-6v4h2V4h4V2zM9.3 12.3l1.4 1.4L16 8.4 14.6 7 9.3 12.3z"/>
                                                    </svg>
                                                </button>
                                            </form>
                                            <% } else if (consultation != null && consultation.getDemande() != null && consultation.getStatut() == ConsultationStatut.TERMINEE && consultation.getDemande().getResponse() != null) { %>
                                            <button type="button" onclick='viewResponse(<%=
                                                "{" +
                                                "\"question\":\"" + consultation.getDemande().getQuestion().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"response\":\"" + consultation.getDemande().getResponse().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
                                                "\"patientCarte\":\"" + consultation.getRecord().getCarte().replace("\"", "\\\"").replace("\n", "\\n") + "\"," +
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
                                    <td colspan="6" class="px-4 py-8 text-center text-gray-500">
                                        <svg class="w-12 h-12 mx-auto mb-3 text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                                        </svg>
                                        Aucune consultation trouvée
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <% if (consultations != null && !consultations.isEmpty()) { %>
                    <div class="flex items-center justify-between mt-4 pt-3 border-t border-gray-200">
                        <div class="text-xs text-gray-600">
                            Total: <span class="font-bold"><%= consultations.size() %></span> consultation(s)
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </main>
    </div>

    <!-- Modal Détails Consultation -->
    <div id="consultationDetailsModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-4xl max-h-[90vh] overflow-hidden transform transition-all animate-scale-in flex flex-col">
            <!-- Header -->
            <div class="bg-gradient-to-r from-green-600 to-teal-600 text-white p-4">
                <div class="flex items-center justify-between">
                    <div class="flex items-center min-w-0 flex-1">
                        <div id="modalAvatar" class="w-10 h-10 rounded-full bg-white/20 backdrop-blur-sm flex items-center justify-center text-white font-bold text-sm shadow-lg mr-3 flex-shrink-0"></div>
                        <div class="min-w-0 flex-1">
                            <h3 class="text-lg font-bold truncate" id="modalPatientName"></h3>
                            <p class="text-xs text-white/80 truncate" id="modalPatientCarte"></p>
                        </div>
                    </div>
                    <button onclick="closeConsultationDetailsModal()" class="text-white/80 hover:text-white transition ml-3 flex-shrink-0">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- Body - Scrollable -->
            <div class="flex-1 overflow-y-auto p-4 space-y-4">
                <!-- Informations Consultation -->
                <div>
                    <div class="flex items-center mb-3">
                        <div class="w-8 h-8 bg-green-100 rounded-lg flex items-center justify-center mr-2 flex-shrink-0">
                            <svg class="w-4 h-4 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"/>
                            </svg>
                        </div>
                        <h4 class="text-base font-bold text-gray-800">Informations de la Consultation</h4>
                    </div>
                    <div class="grid grid-cols-1 gap-3">
                        <div class="bg-green-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Raison</p>
                            <p class="text-sm font-semibold text-gray-800" id="modalRaison"></p>
                        </div>
                        <div class="bg-green-50 p-2.5 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Observations</p>
                            <p class="text-sm text-gray-800 whitespace-pre-wrap" id="modalObservations"></p>
                        </div>
                    </div>
                </div>

                <!-- Actes Techniques -->
                <div>
                    <div class="flex items-center mb-3">
                        <div class="w-8 h-8 bg-teal-100 rounded-lg flex items-center justify-center mr-2 flex-shrink-0">
                            <svg class="w-4 h-4 text-teal-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-6 9l2 2 4-4"/>
                            </svg>
                        </div>
                        <h4 class="text-base font-bold text-gray-800">Actes Techniques</h4>
                    </div>
                    <div class="bg-teal-50 p-2.5 rounded-lg">
                        <p class="text-sm text-gray-800" id="modalActesTechniques"></p>
                    </div>
                </div>

                <!-- Informations Financières -->
                <div>
                    <div class="flex items-center mb-3">
                        <div class="w-8 h-8 bg-blue-100 rounded-lg flex items-center justify-center mr-2 flex-shrink-0">
                            <svg class="w-4 h-4 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
                            </svg>
                        </div>
                        <h4 class="text-base font-bold text-gray-800">Informations Financières</h4>
                    </div>
                    <div class="grid grid-cols-3 gap-2">
                        <div class="bg-blue-50 p-2 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Prix Consultation</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalPrix"></span> DH</p>
                        </div>
                        <div class="bg-blue-50 p-2 rounded-lg">
                            <p class="text-xs text-gray-500 mb-0.5">Actes Techniques</p>
                            <p class="text-sm font-semibold text-gray-800"><span id="modalTotalActes"></span> DH</p>
                        </div>
                        <div class="bg-blue-100 p-2 rounded-lg border-2 border-blue-300">
                            <p class="text-xs text-gray-600 mb-0.5 font-semibold">Total</p>
                            <p class="text-sm font-bold text-blue-800"><span id="modalPrixTotal"></span> DH</p>
                        </div>
                    </div>
                </div>

                <!-- Statut et Dates -->
                <div class="border-t pt-3">
                    <div class="grid grid-cols-1 sm:grid-cols-3 gap-3">
                        <div>
                            <p class="text-xs text-gray-500 mb-1.5">Statut</p>
                            <span id="modalStatus" class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full"></span>
                        </div>
                        <div>
                            <p class="text-xs text-gray-500 mb-0.5">Date de création</p>
                            <p class="text-sm font-semibold text-gray-800 truncate" id="modalCreatedAt"></p>
                        </div>
                        <div>
                            <p class="text-xs text-gray-500 mb-0.5">Dernière modification</p>
                            <p class="text-sm font-semibold text-gray-800 truncate" id="modalUpdatedAt"></p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Footer -->
            <div class="bg-gray-50 p-3 border-t">
                <button onclick="closeConsultationDetailsModal()"
                        class="w-full px-4 py-2 bg-gradient-to-r from-green-600 to-teal-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                    Fermer
                </button>
            </div>
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
                    <div class=" text-gray-600 bg-gray-50 p-2 rounded-lg">
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
                    <label class="text-sm font-semibold text-gray-700 mb-1 block">Réponse de Specialiste</label>
                    <div class="bg-green-50 p-3 rounded-lg border border-green-200">
                        <p class="text-sm text-gray-800 whitespace-pre-wrap" id="viewResponse"></p>
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

    <script src="${pageContext.request.contextPath}/js/consultation.js"></script>
    <script src="${pageContext.request.contextPath}/js/demandes.js"></script>
</body>
</html>