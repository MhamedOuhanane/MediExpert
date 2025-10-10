<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                            <img src="${pageContext.request.contextPath}/images/nurseBackg.jpg"
                                 alt="Logo de fond"
                                 class="w-full bg-contain" />
                        </div>

                        <div class="px-8 pb-8">
                            <!-- Photo de profil -->
                            <div class="flex items-end -mt-16 mb-6">
                                <div class="relative">
                                    <div class="w-32 h-32 rounded-full border-4 border-white shadow-xl bg-gradient-to-br from-blue-300 to-purple-400 flex items-center justify-center">
                                        <img src="${pageContext.request.contextPath}/images/nurseLogo.png"
                                             alt="Logo Infirmier"
                                             class="w-25 h-25 object-contain" />
                                    </div>
                                </div>
                                <div class="ml-6">
                                    <h3 class="text-2xl font-bold text-gray-800">Infirmier(ère)</h3>
                                    <p class="text-gray-600">Professionnel de santé</p>
                                </div>
                            </div>

                            <!-- Informations personnelles -->
                            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">

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
                                            <label class="text-sm font-medium text-gray-600">Nom</label>
                                            <p class="text-gray-800 font-medium">${infirmier.nom}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Prénom</label>
                                            <p class="text-gray-800 font-medium">${infirmier.prenom}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Date de naissance</label>
                                            <p class="text-gray-800 font-medium">${infirmier.dateNaissance}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Numéro de carte</label>
                                            <p class="text-gray-800 font-medium">${infirmier.carte}</p>
                                        </div>
                                    </div>
                                </div>

                                <!-- Coordonnées -->
                                <div class="bg-gradient-to-br from-purple-50 to-blue-50 rounded-xl p-6 border border-purple-100">
                                    <h4 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                                        <svg class="w-5 h-5 mr-2 text-purple-600" fill="currentColor" viewBox="0 0 24 24">
                                            <path d="M20 4H4c-1.1 0-1.99.9-1.99 2L2 18c0 1.1.9 2 2 2h16c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 4l-8 5-8-5V6l8 5 8-5v2z"/>
                                        </svg>
                                        Coordonnées
                                    </h4>
                                    <div class="space-y-3">
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Email</label>
                                            <p class="text-gray-800 font-medium">${infirmier.email}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Téléphone</label>
                                            <p class="text-gray-800 font-medium">${infirmier.telephone}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Rôle</label>
                                            <span class="inline-block px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm font-medium">
                                                Infirmier(ère)
                                            </span>
                                        </div>
                                    </div>
                                </div>

                                <!-- Informations système -->
                                <div class="bg-gradient-to-br from-blue-50 to-purple-50 rounded-xl p-6 border border-blue-100 md:col-span-2">
                                    <h4 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
                                        <svg class="w-5 h-5 mr-2 text-blue-600" fill="currentColor" viewBox="0 0 24 24">
                                            <path d="M11.99 2C6.47 2 2 6.48 2 12s4.47 10 9.99 10C17.52 22 22 17.52 22 12S17.52 2 11.99 2zM12 20c-4.42 0-8-3.58-8-8s3.58-8 8-8 8 3.58 8 8-3.58 8-8 8zm.5-13H11v6l5.25 3.15.75-1.23-4.5-2.67z"/>
                                        </svg>
                                        Informations Système
                                    </h4>
                                    <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Date de création du compte</label>
                                            <p class="text-gray-800 font-medium">${infirmier.getCreatedAtFormatted()}</p>
                                        </div>
                                        <div>
                                            <label class="text-sm font-medium text-gray-600">Dernière mise à jour</label>
                                            <p class="text-gray-800 font-medium">${infirmier.getUpdatedAtFormatted()}</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </main>
    </div>

</body>
</html>
