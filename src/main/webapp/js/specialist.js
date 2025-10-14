let currentWeekOffset = 0;

function getMonday(d) {
    d = new Date(d);
    const day = d.getDay();
    const diff = d.getDate() - day + (day === 0 ? -6 : 1);
    return new Date(d.setDate(diff));
}

function formatDate(date) {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    return `${date.getFullYear()}-${month}-${day}`;
}

function formatDateDisplay(date) {
    const options = { day: 'numeric', month: 'long', year: 'numeric' };
    return new Intl.DateTimeFormat('fr-FR', options).format(date);
}

function tagExpired(cal, slotDiv) {
    const calDate = cal.date;
    const calEndTime = cal.endTime;

    const endDateTime = new Date(
            calDate[0],
            calDate[1] - 1,
            calDate[2],
            calEndTime[0],
            calEndTime[1]
        );
    const today = new Date();
    const isPasse = endDateTime < today;
    if(isPasse) {
        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-not-allowed bg-yellow-100 text-white';
    }
}

const dayNames = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
const startHour = 8;
const endHour = 18;
const slotMinutes = 30;

function renderWeekCalendar() {
    const calendarGrid = document.getElementById('calendarGrid');
    calendarGrid.innerHTML = '';

    const monday = getMonday(new Date());
    monday.setDate(monday.getDate() + (currentWeekOffset * 7));
    const sunday = new Date(monday);
    sunday.setDate(sunday.getDate() + 6);

    const calendrierDataFiltrer = calendrierData.filter(cal => {
        const calDate = new Date(cal.date[0], cal.date[1] - 1, cal.date[2])
        return calDate >= monday && calDate <= sunday;
    });

    const weekRangeEl = document.getElementById('weekRange');
    if (weekRangeEl) {
        weekRangeEl.textContent = `${formatDateDisplay(monday)} - ${formatDateDisplay(sunday)}`;
    }

    const slotsPerHour = 60 / slotMinutes;
    const totalSlots = (endHour - startHour) * slotsPerHour;

    calendarGrid.style.display = 'grid';
    calendarGrid.style.gridTemplateColumns = `50px repeat(${totalSlots}, 30px)`;
    calendarGrid.style.gridAutoRows = '40px';

    const emptyCell = document.createElement('div');
    emptyCell.className = 'border border-gray-300 bg-gray-100';
    calendarGrid.appendChild(emptyCell);

    for (let h = startHour; h < endHour; h++) {
        const hourCell = document.createElement('div');
        hourCell.className = 'border border-gray-300 bg-gray-100 text-xs text-center flex items-center justify-center';
        hourCell.textContent = `${h}:00`;
        hourCell.style.gridColumn = `span ${slotsPerHour}`;
        calendarGrid.appendChild(hourCell);
    }

    dayNames.forEach((dayName, dayIndex) => {

        const dayCell = document.createElement('div');
        dayCell.className = 'border border-gray-300 bg-gray-100 font-bold flex items-center justify-center';
        dayCell.textContent = dayName;
        calendarGrid.appendChild(dayCell);

        for (let h = startHour; h < endHour; h++) {
            for (let half = 0; half < 60; half += slotMinutes) {
                const slotDiv = document.createElement('div');
                slotDiv.className = 'border border-gray-400 flex items-center justify-center text-xs cursor-pointer bg-gray-300';
                const slotTime = `${String(h).padStart(2,'0')}:${half === 0 ? '00' : '30'}`; //important
                const slotDate = const slotDateTime = new Date( monday.getFullYear(), monday.getMonth(), monday.getDate() + dayIndex); //important

                const slotDateTime = new Date(
                    monday.getFullYear(),
                    monday.getMonth(),
                    monday.getDate() + dayIndex,
                    h,
                    half
                );
                const now = new Date();
                if (slotDateTime < now) {
                    slotDiv.classList.add('cursor-not-allowed');
                }

                if (role === 'specialist') {

                }

                const calData = calendrierDataFiltrer[dayIndex];
                if(calData) {
                    const startTimeStr = `${String(calData.startTime[0]).padStart(2,'0')}:${String(calData.startTime[1]).padStart(2,'0')}`;
                    const endTimeStr = `${String(calData.endTime[0]).padStart(2,'0')}:${String(calData.endTime[1]).padStart(2,'0')}`;

                    const isTravail = slotTime >= startTimeStr && slotTime < endTimeStr;

                    if(isTravail) {
                        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-green-100';
                        if (role === 'specialist') {
                            slotDiv.classList.add('hover:bg-green-300');
                        } else if (role === 'generalist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        }
                    }
                }
                if(calData) {
                    tagExpired(calData, slotDiv);
                }

                if(calData && calData.indisponibles) {
                    const isIndispo = calData.indisponibles.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isIndispo) {
                        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-red-500 text-white';
                        if (role === 'generalist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        } else if (role === 'generalist') {

                        }
                    }

                }
                if(calData && calData.reserves) {
                    const isReserve = calData.reserves.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isReserve) {
                        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-orange-500 text-white';
                        if (role === 'specialist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        } else if (role === 'generalist') {

                        }
                    }
                }
                calendarGrid.appendChild(slotDiv);
            }
        }
    });
}

function previousWeek() {
    currentWeekOffset--;
    renderWeekCalendar();
}

function nextWeek() {
    currentWeekOffset++;
    renderWeekCalendar();
}

function fetchIndisponibilite(calId, indiId, _methode, startTime) {
    const data =
}

document.addEventListener('DOMContentLoaded', renderWeekCalendar);
