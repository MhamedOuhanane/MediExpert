<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="bg-gradient-to-br from-purple-50 to-blue-50 rounded-xl p-6 border border-purple-100 md:col-span-2">
    <!-- Navigation semaine -->
    <div class="flex justify-between">
        <h4 class="text-lg font-bold text-gray-800 mb-4 flex items-center">
            <svg class="w-5 h-5 mr-2 text-purple-600" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M19 4h-1V2h-2v2H8V2H6v2H5c-1.11 0-1.99.9-1.99 2L3 20c0 1.1.89 2 2 2h14c1.1 0 2-.9 2-2V6c0-1.1-.9-2-2-2zm0 16H5V10h14v10zM5 8V6h14v2H5zm2 4h10v2H7v-2z"/>
                </svg>
            Calendrier
        </h4>
        <div class="flex justify-between items-center mb-4 w-[50%]">
            <button onclick="previousWeek()" class="p-2 hover:bg-purple-200 rounded-lg transition">
                ←
            </button>
            <h5 id="weekRange"
                class="text-sm font-semibold text-gray-700 w-[90%] truncate text-center"></h5>
            <button onclick="nextWeek()" class="p-2 hover:bg-purple-200 rounded-lg transition">
                →
            </button>
        </div>
    </div>
    <!-- Calendrier hebdomadaire -->
    <div class="overflow-auto p-4 flex justify-center">
        <div class="inline-block">
            <!-- Conteneur principal du calendrier -->
            <div id="calendarGrid" class="grid border border-gray-300"></div>
        </div>
    </div>

    <!-- Légende -->
    <div class="flex items-center justify-center gap-4 mt-4 text-xs">
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-green-500 rounded"></div>
            <span class="text-gray-500">Disponible</span>
        </div>
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-orange-300 rounded"></div>
            <span class="text-gray-500">Réserve</span>
        </div>
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-red-500 rounded"></div>
            <span class="text-gray-500">Indisponible</span>
        </div>
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-blue-300 rounded"></div>
            <span class="text-gray-500">Terminé</span>
        </div>
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-yellow-200 rounded"></div>
            <span class="text-gray-500">Passé</span>
        </div>
        <div class="flex items-center gap-1">
            <div class="w-3 h-3 bg-gray-300 rounded"></div>
            <span class="text-gray-500">Non défini</span>
        </div>
    </div>
</div>

<script>
    const slotMinutes = 30;
    const startHour = 8;
    const endHour = 18;
</script>

<script src="${pageContext.request.contextPath}/js/specialist.js"></script>