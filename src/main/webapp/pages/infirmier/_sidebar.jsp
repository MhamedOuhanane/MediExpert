<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String currentRoute = (String) request.getAttribute("currentRoute");
    if (currentRoute == null) currentRoute = "";
%>
<!-- Sidebar Navigation -->
<aside class="relative w-64 bg-gradient-to-b from-blue-500 to-purple-600 text-white flex flex-col shadow-2xl">

    <!-- Logo -->
    <div class="p-6 border-b border-white border-opacity-20">
        <h1 class="text-3xl font-bold tracking-wider">MediExpert</h1>
    </div>

    <!-- Menu Navigation -->
    <nav class="flex-1 px-4 py-6 space-y-2">
        <a href="/infirmier"
           class="nav-link flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200
           <%= currentRoute.equals("/infirmier") ? "bg-white bg-opacity-20" : "hover:bg-white hover:bg-opacity-20" %>">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
            <span>Mon Profil</span>
        </a>



        <a href="/patients/add"
           class="nav-link flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200
           <%= currentRoute.equals("/patients/add") ? "bg-white bg-opacity-20" : "hover:bg-white hover:bg-opacity-20" %>">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M19 13h-6v6h-2v-6H5v-2h6V5h2v6h6v2z"/>
            </svg>
            <span>Ajouter Patient</span>
        </a>

        <a href="/patients" onclick="showSection('patients-list')"
          class="nav-link flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200
          <%= currentRoute.equals("/patients") ? "bg-white bg-opacity-20" : "hover:bg-white hover:bg-opacity-20" %>">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M16 11c1.66 0 2.99-1.34 2.99-3S17.66 5 16 5c-1.66 0-3 1.34-3 3s1.34 3 3 3zm-8 0c1.66 0 2.99-1.34 2.99-3S9.66 5 8 5C6.34 5 5 6.34 5 8s1.34 3 3 3zm0 2c-2.33 0-7 1.17-7 3.5V19h14v-2.5c0-2.33-4.67-3.5-7-3.5zm8 0c-.29 0-.62.02-.97.05 1.16.84 1.97 1.97 1.97 3.45V19h6v-2.5c0-2.33-4.67-3.5-7-3.5z"/>
            </svg>
            <span>Liste des Patients</span>
        </a>
    </nav>

    <!-- Bouton Déconnexion -->
    <div class="p-4 border-t border-white border-opacity-20">
        <a href="${pageContext.request.contextPath}/logout" class="flex items-center space-x-3 px-4 py-3 rounded-lg bg-purple-700 hover:bg-purple-00 transition duration-200">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.58L17 17l5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z"/>
            </svg>
            <span>Déconnexion</span>
        </a>
    </div>
</aside>
