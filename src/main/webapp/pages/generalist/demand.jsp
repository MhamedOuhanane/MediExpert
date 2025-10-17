<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mediexpert.model.Specialiste" %>
<%@ page import="com.mediexpert.enums.SpecialisteType" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.UUID" %>

<%
    List<Specialiste> specialistes = (List<Specialiste>) request.getAttribute("specialistes");
    String calendriersMap = (String) request.getAttribute("calendriersMap");
    UUID consultationId = (UUID) request.getAttribute("consultationId");
%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="${pageContext.request.contextPath}/partials/_head.jsp" />
<body class="bg-gradient-to-br from-blue-50 via-purple-50 to-blue-100 min-h-screen">

    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <%@ include file="_sidebar.jsp" %>

        <main class="flex-1 overflow-y-auto p-3">
            <!-- Section: Liste des Spécialistes -->
            <div id="specialists-list-section">
                <div class="bg-white/90 backdrop-blur-sm rounded-2xl shadow-xl p-4">
                    <div class="flex justify-between items-center mb-4">
                        <h3 class="text-xl font-bold text-gray-800 flex items-center">
                            <svg class="w-7 h-7 mr-2 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                            </svg>
                            Choisir un Spécialiste
                        </h3>

                        <!-- Filtre par spécialité -->
                        <div class="flex items-center gap-2">
                            <label for="specialiteFilter" class="text-sm font-semibold text-gray-700">Spécialité:</label>
                            <select id="specialiteFilter" onchange="filterBySpecialite()" class="px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-purple-500">
                                <option value="">Toutes les spécialités</option>
                                <% for (SpecialisteType type : SpecialisteType.values()) { %>
                                    <option value="<%= type.name() %>"><%= type.name().replace("_", " ") %></option>
                                <% } %>
                            </select>
                        </div>
                    </div>

                    <div class="overflow-x-auto">
                        <table class="w-full text-sm">
                            <thead class="bg-gradient-to-r from-purple-50 to-blue-50">
                                <tr>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Spécialiste</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Spécialité</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Description</th>
                                    <th class="px-4 py-3 text-left text-xs font-bold text-gray-700 uppercase">Tarif</th>
                                    <th class="px-4 py-3 text-center text-xs font-bold text-gray-700 uppercase">Action</th>
                                </tr>
                            </thead>
                            <tbody class="bg-white divide-y divide-gray-200" id="specialistsTableBody">
                                <%
                                if (specialistes != null && !specialistes.isEmpty()) {
                                    for (Specialiste specialiste : specialistes) {
                                        String specialisteName = specialiste.getPrenom() + " " + specialiste.getNom();
                                        String initials = specialiste.getPrenom().substring(0, 1).toUpperCase() +
                                                        specialiste.getNom().substring(0, 1).toUpperCase();
                                        String specialiteType = specialiste.getSpecialite().name();
                                        String specialiteDesc = specialiste.getSpecialite().getDescription();
                                %>
                                <tr class="specialist-row hover:bg-gradient-to-r hover:from-purple-50 hover:to-blue-50 transition duration-200" data-specialite="<%= specialiteType %>">
                                    <!-- Spécialiste -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex items-center">
                                            <div class="w-9 h-9 rounded-full bg-gradient-to-br from-purple-400 to-blue-500 flex items-center justify-center text-white font-bold text-xs shadow-md">
                                                <%= initials %>
                                            </div>
                                            <div class="ml-3">
                                                <div class="text-sm font-bold text-gray-900">
                                                    <%= specialisteName %>
                                                </div>
                                                <div class="text-xs text-gray-500">
                                                    <%= specialiste.getEmail() %>
                                                </div>
                                            </div>
                                        </div>
                                    </td>

                                    <!-- Spécialité -->
                                    <td class="px-4 py-3">
                                        <span class="px-2.5 py-1 inline-flex text-xs leading-5 font-semibold rounded-full bg-purple-100 text-purple-800 border border-purple-300">
                                            <%= specialiteType.replace("_", " ") %>
                                        </span>
                                    </td>

                                    <!-- Description -->
                                    <td class="px-4 py-3">
                                        <div class="text-sm text-gray-900 max-w-xs truncate" title="<%= specialiteDesc %>">
                                            <%= specialiteDesc %>
                                        </div>
                                    </td>

                                    <!-- Tarif -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <span class="text-sm font-semibold text-gray-900"><%= String.format("%.2f", specialiste.getTarif()) %> DH</span>
                                    </td>

                                    <!-- Action -->
                                    <td class="px-4 py-3 whitespace-nowrap">
                                        <div class="flex justify-center">
                                            <button type="button"
                                                    onclick='openDemandModal(<%=
                                                        "{" +
                                                        "\"specialisteId\":\"" + specialiste.getId() + "\"," +
                                                        "\"specialisteName\":\"" + specialisteName + "\"," +
                                                        "\"specialiteType\":\"" + specialiteType.replace("_", " ") + "\"," +
                                                        "\"tarif\":\"" + String.format("%.2f", specialiste.getTarif()) + "\"" +
                                                        "}"
                                                    %>)'
                                                    class="px-4 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                                                Choisir
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
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"/>
                                        </svg>
                                        Aucun spécialiste trouvé
                                    </td>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>

                    <% if (specialistes != null && !specialistes.isEmpty()) { %>
                    <div class="flex items-center justify-between mt-4 pt-3 border-t border-gray-200">
                        <div class="text-xs text-gray-600">
                            Total: <span class="font-bold" id="totalSpecialists"><%= specialistes.size() %></span> spécialiste(s)
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>
        </main>
    </div>

    <!-- Modal Créer Demande -->
    <div id="demandModal" class="hidden fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4">
        <div class="bg-white rounded-2xl shadow-2xl w-full max-w-6xl max-h-[90vh] overflow-hidden transform transition-all animate-scale-in flex flex-col">
            <!-- Header -->
            <div class="bg-gradient-to-r from-purple-600 to-blue-600 text-white p-4">
                <div class="flex items-center justify-between">
                    <h3 class="text-lg font-bold">Créer une Demande</h3>
                    <button onclick="closeDemandModal()" class="text-white/80 hover:text-white transition">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"/>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- Body - Scrollable -->
            <div class="flex-1 overflow-y-auto p-4">
                <form id="demandForm" action="${pageContext.request.contextPath}/demandes" method="POST" class="space-y-4">
                    <input type="hidden" name="csrfToken" value="${csrfToken}" />
                    <input type="hidden" name="consultationId" value="<%= consultationId %>" />
                    <input type="hidden" name="specialisteId" id="specialisteIdInput" />
                    <input type="hidden" name="prix" id="prixInput" />
                    <input type="hidden" name="startDate" id="startDateInput" />

                    <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                        <!-- Informations Spécialiste -->
                        <div class="space-y-4">
                            <div>
                                <h4 class="text-base font-bold text-gray-800 mb-3 flex items-center">
                                    <svg class="w-5 h-5 mr-2 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                                    </svg>
                                    Spécialiste Sélectionné
                                </h4>
                            </div>

                            <div class="bg-purple-50 p-3 rounded-lg">
                                <label class="block text-xs font-semibold text-gray-700 mb-1">Nom Complet</label>
                                <p id="specialisteNameDisplay"
                                   class="w-full px-3 py-2 text-sm text-gray-900 font-semibold">
                                </p>
                            </div>

                            <div class="bg-purple-50 p-3 rounded-lg">
                                <label class="block text-xs font-semibold text-gray-700 mb-1">Spécialité</label>
                                <p id="specialiteTypeDisplay"
                                   class="w-full px-3 py-2 text-sm text-gray-900">
                                </p>
                            </div>

                            <div class="bg-purple-50 p-3 rounded-lg">
                                <label class="block text-xs font-semibold text-gray-700 mb-1">Tarif</label>
                                <p id="tarifDisplay"
                                   class="w-full px-3 py-2 text-sm text-gray-900 font-semibold">
                                </p>
                            </div>


                            <div class="bg-blue-50 p-3 rounded-lg border-2 border-blue-200">
                                <label class="block text-xs font-semibold text-gray-700 mb-1">Question<span class="text-red-500">*</span></label>
                                <textarea name="question" id="questionInput" required rows="4"
                                          placeholder="Décrivez votre question pour le spécialiste..."
                                          class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-purple-500"></textarea>
                            </div>
                        </div>

                        <!-- Calendrier -->
                        <div id="calendrierContainer" class="md:col-span-2">
                            <%@ include file="../../partials/_calendrier.jsp" %>
                        </div>
                    </div>
                </form>
            </div>

            <!-- Footer -->
            <div class="bg-gray-50 p-3 border-t flex gap-2">
                <button type="button" onclick="closeDemandModal()"
                        class="flex-1 px-4 py-2 bg-gray-300 text-gray-700 font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                    Annuler
                </button>
                <button type="submit" form="demandForm"
                        class="flex-1 px-4 py-2 bg-gradient-to-r from-purple-600 to-blue-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition text-sm">
                    Créer la Demande
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
        const calendriersMap = <%= calendriersMap != null ? calendriersMap : "{}" %>;

        window.role = 'generalist';
    </script>

    <!-- Scripts -->
    <script src="${pageContext.request.contextPath}/js/specialist.js"></script>
    <script src="${pageContext.request.contextPath}/js/demandG.js"></script>
</body>
</html>