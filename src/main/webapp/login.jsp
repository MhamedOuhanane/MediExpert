<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<%@ include file="partials/_head.jsp" %>
<body class="bg-gradient-to-br from-blue-50 via-blue-100 to-purple-50 min-h-screen flex items-center justify-center p-4">
    <div class="w-full max-w-5xl bg-white rounded-3xl shadow-2xl overflow-hidden flex flex-col md:flex-row">

        <div class="w-full md:w-1/2 p-8 md:p-12">
            <h1 class="text-3xl font-bold text-gray-800 mb-6">Connexion</h1>

            <p class="text-gray-600 mb-8">Bienvenue sur la Plateforme MediExpert</p>

            <!-- Message d'erreur -->
            <%
                String errorMessage = (String) request.getAttribute("errorMessage");
                if (errorMessage != null) {
            %>
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded mb-4">
                    <p class="text-sm"><%= errorMessage %></p>
                </div>
            <% } %>

            <!-- Formulaire de connexion -->
            <form action="${pageContext.request.contextPath}/connection" method="POST" class="space-y-10">

                <input id="csrfToken" type="hidden" name="csrfToken" value="${csrfToken}" >
                <div class="space-y-6">
                    <!-- Champ Email d'utilisateur -->
                    <div>
                        <label for="email" class="block text-sm font-medium text-gray-700 mb-2">
                            Adresse e-mail
                        </label>
                        <div class="relative">
                            <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"/>
                                </svg>
                            </span>
                            <input
                                type="text"
                                id="email"
                                name="email"
                                required
                                class="w-[70%] pl-10 pr-4 py-1 border border-gray-300 rounded-sm focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                                placeholder="Entrez votre adresse e-mail"
                            >
                        </div>
                    </div>

                    <!-- Champ Mot de passe -->
                    <div>
                        <label for="password" class="block text-sm font-medium text-gray-700 mb-2">
                            Mot de passe
                        </label>
                        <div class="relative">
                            <span class="absolute inset-y-0 left-0 flex items-center pl-3">
                                <svg class="w-5 h-5 text-gray-400" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z"/>
                                </svg>
                            </span>
                            <input
                                type="password"
                                id="password"
                                name="password"
                                required
                                class="w-[70%] pl-10 pr-4 py-1 border border-gray-300 rounded-sm focus:ring-2 focus:ring-blue-500 focus:border-transparent outline-none transition"
                                placeholder="Entrez votre mot de passe"
                            >
                        </div>
                    </div>
                </div>

                <!-- Bouton de connexion -->
                <button
                    type="submit"
                    class="w-full bg-blue-600 text-white py-2 rounded-lg font-semibold hover:bg-blue-700 transition duration-200 shadow-md hover:shadow-lg"
                >
                    Connexion
                </button>
            </form>
        </div>

        <!-- Section Illustration (Droite) -->
        <div class="hidden md:flex w-full md:w-1/2 bg-gradient-to-br from-blue-400 via-blue-500 to-purple-500 items-center justify-center p-12 relative overflow-hidden">

            <!-- Formes décoratives -->
            <div class="absolute top-0 right-0 w-64 h-64 bg-white opacity-10 rounded-full -mr-32 -mt-32"></div>
            <div class="absolute bottom-0 left-0 w-48 h-48 bg-white opacity-10 rounded-full -ml-24 -mb-24"></div>

            <!-- Contenu central -->
            <div class="text-center z-10">
                <div class="bg-white bg-opacity-20 backdrop-blur-sm rounded-2xl p-8 shadow-xl">
                    <svg class="w-32 h-32 mx-auto mb-6 text-white" fill="currentColor" viewBox="0 0 24 24">
                        <path d="M19 3H5c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm-7 3c1.93 0 3.5 1.57 3.5 3.5 0 1.93-1.57 3.5-3.5 3.5s-3.5-1.57-3.5-3.5c0-1.93 1.57-3.5 3.5-3.5zm7 13H5v-.23c0-2.31 3.07-4.27 7-4.27s7 1.96 7 4.27V19z"/>
                    </svg>
                    <h2 class="text-2xl font-bold text-white mb-3">
                        MediExpert
                    </h2>
                    <p class="text-white text-opacity-90">
                        MediExpert

                        Accédez à votre espace professionnel pour gérer vos dossiers médicaux, consultations et rapports d’expertise.
                    </p>
                </div>
            </div>

            <!-- Icônes médicales décoratives -->
            <div class="absolute top-10 left-10 text-white opacity-20">
                <svg class="w-16 h-16" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M12 2L4 5v6.09c0 5.05 3.41 9.76 8 10.91 4.59-1.15 8-5.86 8-10.91V5l-8-3zm-1 16h2v-6h-2v6zm0-8h2V6h-2v4z"/>
                </svg>
            </div>
            <div class="absolute bottom-10 right-10 text-white opacity-20">
                <svg class="w-16 h-16" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M19 3h-1V1h-2v2H8V1H6v2H5c-1.11 0-1.99.9-1.99 2L3 19c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V5c0-1.1-.9-2-2-2zm0 16H5V8h14v11zM7 10h5v5H7z"/>
                </svg>
            </div>
        </div>
    </div>

</body>
</html>