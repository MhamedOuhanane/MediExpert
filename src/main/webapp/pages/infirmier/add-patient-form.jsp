<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mediexpert.model.Record" %>

<%
    Record patient = (Record) request.getAttribute("patient");
    Boolean existence = (Boolean) request.getAttribute("existence");
%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="/partials/_head.jsp" />

<body class="bg-gradient-to-br from-indigo-50 via-white to-purple-50">
    <div class="flex h-screen overflow-hidden">
        <%@ include file="_sidebar.jsp" %>

        <main class="flex-1 overflow-y-auto p-6">
            <div class="flex items-center space-x-2 mb-4">
                <div class="w-9 h-9 bg-gradient-to-br from-blue-500 to-purple-600 rounded-lg flex items-center justify-center shadow-md">
                    <svg class="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2.5" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                    </svg>
                </div>
                <div>
                    <h1 class="text-xl font-bold bg-gradient-to-r from-blue-600 to-purple-600 bg-clip-text text-transparent">
                        <%= existence == null ? "Recherche du patient" : existence ? "Modifier Patient" : "Nouveau Patient" %>
                    </h1>
                </div>
            </div>
            <div class="backdrop-blur-xl rounded-2xl shadow-xl border border-gray-100 p-6 animate-slide-up">
                 <% if (existence == null) { %>
                    <form action="<%= request.getContextPath() %>/patients/find" method="POST" class="mb-6">
                        <div class="flex items-end space-x-3">
                            <input id="csrfToken" type="hidden" name="csrfToken" value="${csrfToken}" >
                            <div class="flex-1">
                                <label class="block text-sm font-semibold text-gray-700 mb-1">N° Sécurité Sociale</label>
                                <input type="text" name="carte" required placeholder="Ex: ABC123456" class="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-md focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition">
                            </div>
                            <button type="submit" class="px-5 py-2 bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold rounded-md shadow-md hover:shadow-lg transition-all">Rechercher</button>
                        </div>
                    </form>
                <% } else { %>
                    <% if (existence && patient != null) { %>
                        <div class="mb-4 text-green-600 font-medium">Patient trouvé — Vous pouvez le modifier.</div>
                    <% } else { %>
                        <div class="mb-4 text-blue-600 font-medium">Aucun patient trouvé — Vous pouvez l’ajouter.</div>
                    <% } %>
                    <form action="<%= existence ? (request.getContextPath() + "/patients/update") : (request.getContextPath() + "/patients/add") %>" method="POST">

                        <input type="hidden" name="csrfToken" value="${csrfToken}">

                        <!-- Section Informations -->
                        <div class="p-4 border-b border-gray-100">
                            <div class="flex items-center mb-3">
                                <div class="w-7 h-7 bg-blue-100 rounded-md flex items-center justify-center mr-2">
                                    <svg class="w-3.5 h-3.5 text-blue-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                                    </svg>
                                </div>
                                <h2 class="text-sm font-bold text-gray-800">Informations</h2>
                            </div>

                            <div class="grid grid-cols-1 md:grid-cols-3 gap-3">
                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Nom *</label>
                                    <input type="text" name="nom" required
                                           value="<%= patient != null ? patient.getNom() : "" %>"
                                           <%= existence ? "readonly" : "" %>
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-blue-500 focus:border-blue-500 outline-none <%= existence ? "bg-gray-50" : "" %>">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Prénom *</label>
                                    <input type="text" name="prenom" required
                                           value="<%= patient != null ? patient.getPrenom() : "" %>"
                                           <%= existence ? "readonly" : "" %>
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-blue-500 focus:border-blue-500 outline-none <%= existence ? "bg-gray-50" : "" %>">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Date de naissance *</label>
                                    <input type="date" name="dateNaissance" required
                                           value="<%= patient != null ? patient.getDateNaissance() : "" %>"
                                           <%= existence ? "readonly" : "" %>
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-blue-500 focus:border-blue-500 outline-none <%= existence ? "bg-gray-50" : "" %>">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">N° Sécurité Sociale *</label>
                                    <input type="text" name="carte" required
                                           value="<%= patient != null ? patient.getCarte() : "" %>"
                                           <%= existence ? "readonly" : "" %>
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-blue-500 focus:border-blue-500 outline-none <%= existence ? "bg-gray-50" : "" %>">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Téléphone</label>
                                    <input type="tel" name="telephone"
                                           value="<%= patient != null ? patient.getTelephone() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-blue-500 focus:border-blue-500 outline-none">
                                </div>
                            </div>
                        </div>

                        <!-- Section Signes Vitaux -->
                        <div class="p-4 bg-gradient-to-br from-green-50/30 to-emerald-50/30 border-b border-gray-100">
                            <div class="flex items-center mb-3">
                                <div class="w-7 h-7 bg-green-100 rounded-md flex items-center justify-center mr-2">
                                    <svg class="w-3.5 h-3.5 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"/>
                                    </svg>
                                </div>
                                <h2 class="text-sm font-bold text-gray-800">Signes Vitaux</h2>
                            </div>

                            <div class="grid grid-cols-2 md:grid-cols-4 gap-3">
                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Tension</label>
                                    <input type="number" name="tension" placeholder="120/80"
                                           value="<%= patient != null ? patient.getTension() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-green-500 focus:border-green-500 outline-none bg-white">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">FC (bpm)</label>
                                    <input type="number" name="frequenceCardiaque" placeholder="75"
                                           value="<%= patient != null ? patient.getFrequenceCardiaque() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-green-500 focus:border-green-500 outline-none bg-white">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Temp. (°C)</label>
                                    <input type="number" step="0.1" name="temperature" placeholder="37.0"
                                           value="<%= patient != null ? patient.getTemperature() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-green-500 focus:border-green-500 outline-none bg-white">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">FR (/min)</label>
                                    <input type="number" name="frequenceRespiratoire" placeholder="16"
                                           value="<%= patient != null ? patient.getFrequenceRespiratoire() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-green-500 focus:border-green-500 outline-none bg-white">
                                </div>
                            </div>
                        </div>

                        <!-- Section Mesures -->
                        <div class="p-4">
                            <div class="flex items-center mb-3">
                                <div class="w-7 h-7 bg-purple-100 rounded-md flex items-center justify-center mr-2">
                                    <svg class="w-3.5 h-3.5 text-purple-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 6l3 1m0 0l-3 9a5.002 5.002 0 006.001 0M6 7l3 9M6 7l6-2m6 2l3-1m-3 1l-3 9a5.002 5.002 0 006.001 0M18 7l3 9m-3-9l-6-2m0-2v2m0 16V5m0 16H9m3 0h3"/>
                                    </svg>
                                </div>
                                <h2 class="text-sm font-bold text-gray-800">Mesures</h2>
                            </div>

                            <div class="grid grid-cols-2 gap-3">
                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Poids (kg)</label>
                                    <input type="number" step="0.1" name="poids" placeholder="70.0"
                                           value="<%= patient != null ? patient.getPoids() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-purple-500 focus:border-purple-500 outline-none">
                                </div>

                                <div>
                                    <label class="block text-xs font-semibold text-gray-700 mb-1">Taille (m)</label>
                                    <input type="number" step="0.01" name="taille" placeholder="1.75"
                                           value="<%= patient != null ? patient.getTaille() : "" %>"
                                           class="w-full px-2.5 py-1.5 text-sm border-2 border-gray-200 rounded-lg focus:ring-1 focus:ring-purple-500 focus:border-purple-500 outline-none">
                                </div>
                            </div>
                        </div>

                        <!-- Actions -->
                        <div class="px-4 py-3 bg-gray-50 flex justify-end space-x-3 rounded-b-2xl">
                            <button type="reset"
                                    class="px-5 py-2 text-sm border-2 border-gray-300 text-gray-700 font-semibold rounded-lg hover:bg-white hover:border-gray-400 transition-all">
                                Réinitialiser
                            </button>
                            <button type="submit"
                                    class="px-5 py-2 text-sm bg-gradient-to-r from-blue-600 to-purple-600 text-white font-semibold rounded-lg shadow-md hover:shadow-lg transition-all">
                                <%= existence ? "Mettre à jour" : "Enregistrer" %>
                            </button>
                        </div>
                    </form>
                <% } %>
            </div>
        </main>
    </div>

    <style>
        @keyframes fade-in {
            from { opacity: 0; transform: translateY(-10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes slide-up {
            from { opacity: 0; transform: translateY(15px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .animate-fade-in { animation: fade-in 0.4s ease-out; }
        .animate-slide-up { animation: slide-up 0.5s ease-out; }

        input:focus { transform: translateY(-1px); }
    </style>
</body>
</html>