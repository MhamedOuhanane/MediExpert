<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../partials/_message.jsp" %>
<%
    String currentRoute = (String) request.getAttribute("currentRoute");
    if (currentRoute == null) currentRoute = "";
%>
<!-- Sidebar Navigation -->
<aside class="w-64 bg-gradient-to-b from-blue-500 to-purple-600 text-white flex flex-col shadow-2xl">

    <!-- Logo -->
    <div class="p-6 border-b border-white border-opacity-20">
        <h1 class="text-3xl font-bold tracking-wider">MediExpert</h1>
    </div>

    <!-- Menu Navigation -->
    <nav class="flex-1 px-4 py-6 space-y-2">
        <a href="/specialist"
           class="nav-link flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200
           <%= currentRoute.equals("/specialist") ? "bg-white bg-opacity-20" : "hover:bg-white hover:bg-opacity-20" %>">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
            </svg>
            <span>Mon Profil</span>
        </a>

        <a href="/demandes"
          class="nav-link flex items-center space-x-3 px-4 py-3 rounded-lg transition duration-200
          <%= currentRoute.equals("/demandes") ? "bg-white bg-opacity-20" : "hover:bg-white hover:bg-opacity-20" %>">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
              <path d="M4 4h10v2H6v12h12V8h2v10a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6c0-1.1.9-2 2-2zM20 2h-6v4h2V4h4V2zM9.3 12.3l1.4 1.4L16 8.4 14.6 7 9.3 12.3z"/>
            </svg>
            <span>Demandes d'Avis</span>
        </a>
    </nav>

    <!-- Bouton Déconnexion -->
    <div class="p-4 border-t border-white border-opacity-20">
        <a href="${pageContext.request.contextPath}/auth/logout" class="flex items-center space-x-3 px-4 py-3 rounded-lg bg-purple-700 hover:bg-purple-00 transition duration-200">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24">
                <path d="M17 7l-1.41 1.41L18.17 11H8v2h10.17l-2.58 2.58L17 17l5-5zM4 5h8V3H4c-1.1 0-2 .9-2 2v14c0 1.1.9 2 2 2h8v-2H4V5z"/>
            </svg>
            <span>Déconnexion</span>
        </a>
    </div>
</aside>
