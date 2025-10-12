<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String message = (String) session.getAttribute("successMessage");
    String error = (String) session.getAttribute("errorMessage");
    session.removeAttribute("successMessage");
    session.removeAttribute("errorMessage");
%>

<div id="messageBox" class="fixed top-4 right-4 space-y-2 z-50 transition-opacity duration-500">
    <% if (message != null) { %>
        <div class="bg-green-100 text-green-800 p-3 rounded-md shadow-md flex items-center space-x-2">
            <span><%= message %></span>
        </div>
    <% } else if (error != null) { %>
        <div class="bg-red-100 text-red-800 p-3 rounded-md shadow-md flex items-center space-x-2">
            <span><%= error %></span>
        </div>
    <% } %>
</div>

<script>
    const msgBox = document.getElementById('messageBox');
    if (msgBox && msgBox.innerHTML.trim() !== "") {
        setTimeout(() => {
            msgBox.style.opacity = "0";
            setTimeout(() => msgBox.remove(), 500);
        }, 6000);
    }
</script>
