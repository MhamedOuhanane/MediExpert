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

function tagExpired(slotDate, slotTime, slotDiv) {
    const [hours, minutes] = slotTime.split(':').map(Number);
    const slotDateTime = new Date(
        slotDate.getFullYear(),
        slotDate.getMonth(),
        slotDate.getDate(),
        hours,
        minutes + 30
    );
    const now = new Date();
    return slotDateTime < now;
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
                const slotDate = new Date( monday.getFullYear(), monday.getMonth(), monday.getDate() + dayIndex); //important

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
                            const indispo = calData.indisponibles.find(ind =>
                                slotTime >= ind.startTime && slotTime < ind.endTime
                            );

                            slotDiv.onclick = () => {
                                fetchIndisponibilite({
                                    startTime: slotTime,
                                    calendrier_id: calData.id,
                                    _methode: "POST"
                                }, slotDiv);
                            };
                        } else if (role === 'generalist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        }
                    }
                }
                if(calData && tagExpired(slotDate, slotTime, slotDiv)) {
                    slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-not-allowed bg-yellow-100 text-white';
                }

                if(calData && calData.indisponibles) {
                    const isIndispo = calData.indisponibles.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isIndispo) {
                        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-red-400 text-white';
                        if (role === 'generalist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        } else if (role === 'specialist') {
                            const indispo = calData.indisponibles.find(ind =>
                                slotTime >= ind.startTime && slotTime < ind.endTime
                            );

                            slotDiv.onclick = () => {
                                fetchIndisponibilite({
                                    id: indispo.id,
                                    calendrier_id: calData.id,
                                    _methode: "DELETE"
                                }, slotDiv);
                            };
                        }
                    }

                }
                if(calData && calData.reserves) {
                    const isReserve = calData.reserves.some(ind =>
                        slotTime >= ind.startTime && slotTime < ind.endTime
                    );
                    if(isReserve) {
                        const isTermine = calData.reserves.some(ind =>
                            ind.status === "TERMINEE"
                        );
                        slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer text-white';
                        if (isTermine) {
                            slotDiv.classList.add('bg-blue-200', 'cursor-not-allowed', 'pointer-events-none');
                        } else {
                            if (role === 'generalist') {

                            }
                        }
                        if (role === 'specialist') {
                            slotDiv.classList.add('cursor-not-allowed', 'pointer-events-none');
                        }
                    }
                }
                if(calData && tagExpired(slotDate, slotTime, slotDiv)) {
                    slotDiv.classList.add('cursor-not-allowed');
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

function fetchIndisponibilite(data, slotDiv) {
    const url = data._methode === 'POST'
        ? '/indisponibles'
        : `/indisponibles/${data.id}`;

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(res => {
        if (!res.ok) {
            return res.text().then(text => {
                console.error('Erreur serveur:', res.status, text);
                throw new Error('Erreur serveur');
            });
        }
        return res.json();
    })
    .then(result => {
        console.log('Réponse serveur:', result);

        if (data._methode === 'DELETE' && result.deleted === true) {
            const cal = calendrierData.find(c => c.id === data.calendrier_id);
            if (cal && Array.isArray(cal.indisponibles)) {
                cal.indisponibles = cal.indisponibles.filter(ind => ind.id !== data.id);
            }
        }

        if (data._methode === 'POST' && result.id) {
            const cal = calendrierData.find(c => c.id === result.calendrierId);
            if (cal) {
                if (!Array.isArray(cal.indisponibles)) cal.indisponibles = [];
                cal.indisponibles.push({
                    id: result.id,
                    startTime: result.startTime,
                    endTime: result.endTime
                });
            }
        }

        renderWeekCalendar();
    })
    .catch(err => {
        console.error('❌ Erreur:', err);
    });
}


document.addEventListener('DOMContentLoaded', renderWeekCalendar);
