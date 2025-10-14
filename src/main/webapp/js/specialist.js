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

const dayNames = ['Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam', 'Dim'];
const startHour = 8;
const endHour = 17;
const slotMinutes = 30;

function renderWeekCalendar() {
    const calendarGrid = document.getElementById('calendarGrid');
    calendarGrid.innerHTML = '';

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
                slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-green-100 hover:bg-green-300';

                const slotTime = `${String(h).padStart(2,'0')}:${half === 0 ? '00' : '30'}`;
                slotDiv.onclick = () => alert(`${dayName} - ${slotTime}`);

                const calData = calendrierData[dayIndex];
                if(calData && calData.indisponibles) {
                    const isIndispo = calData.indisponibles.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isIndispo) slotDiv.className = 'border border-gray-300 min-w-[60px] flex items-center justify-center text-xs cursor-pointer bg-gray-500 text-white';
                }
                if(calData && calData.reserves) {
                    const isReserve = calData.reserves.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isReserve) slotDiv.className = 'border border-gray-300 min-w-[60px] flex items-center justify-center text-xs cursor-pointer bg-orange-500 text-white';
                }
                if (dayName === 'Dim') {
                    slotDiv.className = 'border border-gray-300 min-w-[60px] flex items-center justify-center text-xs cursor-pointer bg-gray-500 text-white';
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

document.addEventListener('DOMContentLoaded', renderWeekCalendar);