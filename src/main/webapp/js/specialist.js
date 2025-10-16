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

function timeArrayToString(timeArray) {
    if (!timeArray || timeArray.length < 2) return null;
    return `${String(timeArray[0]).padStart(2, '0')}:${String(timeArray[1]).padStart(2, '0')}`;
}

function tagExpired(slotDate, slotTime) {
    const [hours, minutes] = slotTime.split(':').map(Number);
    const slotDateTime = new Date(
        slotDate.getFullYear(),
        slotDate.getMonth(),
        slotDate.getDate(),
        hours,
        minutes
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
        const calDate = new Date(cal.date[0], cal.date[1] - 1, cal.date[2]);
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
                const slotTime = `${String(h).padStart(2, '0')}:${half === 0 ? '00' : '30'}`;
                const slotDate = new Date(monday.getFullYear(), monday.getMonth(), monday.getDate() + dayIndex);

                slotDiv.className = 'border border-gray-400 flex items-center justify-center text-xs bg-gray-300';
                slotDiv.classList.add(role === 'specialist' ? 'cursor-pointer' : '');

                const calData = calendrierDataFiltrer[dayIndex];

                const isExpired = tagExpired(slotDate, slotTime);

                if (calData) {
                    const startTimeStr = timeArrayToString(calData.startTime);
                    const endTimeStr = timeArrayToString(calData.endTime);
                    const isTravail = slotTime >= startTimeStr && slotTime < endTimeStr;

                    if (isTravail) {
                        const reserve = calData.reserves?.find(res =>
                            timeArrayToString(res.startTime) <= slotTime &&
                            slotTime < timeArrayToString(res.endTime)
                        );

                        if (reserve) {
                            slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs text-white';
                            if (reserve.status === "TERMINEE") {
                                slotDiv.classList.add('bg-blue-200', 'cursor-not-allowed', 'pointer-events-none');
                            } else {
                                slotDiv.classList.add('bg-orange-300', 'cursor-not-allowed', 'pointer-events-none');
                            }
                        } else {
                            const indispo = calData.indisponibles?.find(ind =>
                                timeArrayToString(ind.startTime) <= slotTime &&
                                slotTime < timeArrayToString(ind.endTime)
                            );

                            if (indispo) {
                                slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs bg-red-400 text-white';

                                if (isExpired) {
                                    slotDiv.classList.add('cursor-not-allowed');
                                } else if (role === 'specialist') {
                                    slotDiv.classList.add('cursor-pointer', 'hover:bg-red-500');
                                    slotDiv.onclick = () => {
                                        fetchIndisponibilite({
                                            id: indispo.id,
                                            calendrier_id: calData.id,
                                            _methode: "DELETE"
                                        }, slotDiv);
                                    };
                                }
                            } else {
                                if (isExpired) {
                                    slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-not-allowed bg-yellow-200';
                                } else {
                                    slotDiv.className = 'border border-gray-300 flex items-center justify-center text-xs cursor-pointer bg-green-400';
                                    if (role === 'specialist') {
                                        slotDiv.classList.add('hover:bg-green-500');
                                        slotDiv.onclick = () => {
                                            fetchIndisponibilite({
                                                startTime: slotTime,
                                                calendrier_id: calData.id,
                                                _methode: "POST"
                                            }, slotDiv);
                                        };
                                    }
                                }
                            }
                        }
                    }
                }
                if (isExpired) {
                    slotDiv.classList.add('cursor-not-allowed');
                }
                slotDiv.title = `time: ${slotTime}`;

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

let isProcessing = false;

function fetchIndisponibilite(data, slotDiv) {
    if (isProcessing) {
        return;
    }

    isProcessing = true;
    slotDiv.style.opacity = '0.5';
    slotDiv.style.pointerEvents = 'none';

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
                throw new Error(`Erreur ${res.status}: ${text}`);
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
                console.log('Indisponibilité supprimée:', data.id);
            }
        }

        if (data._methode === 'POST' && result.id) {
            const cal = calendrierData.find(c => c.id === result.calendrierId);
            if (cal) {
                if (!Array.isArray(cal.indisponibles)) cal.indisponibles = [];

                const exists = cal.indisponibles.some(ind => ind.id === result.id);
                if (!exists) {
                    const [h, m] = result.startTime.split(':').map(Number);
                    const [eh, em] = result.endTime.split(':').map(Number);

                    cal.indisponibles.push({
                        id: result.id,
                        startTime: [h, m],
                        endTime: [eh, em]
                    });
                    console.log('Indisponibilité ajoutée:', result.id);
                } else {
                    console.log('ℹIndisponibilité déjà existante:', result.id);
                }
            }
        }

        renderWeekCalendar();
    })
    .catch(err => {
        console.error('Erreur lors de la requête:', err);
        slotDiv.style.opacity = '1';
        slotDiv.style.pointerEvents = 'auto';
    })
    .finally(() => {
        setTimeout(() => {
            isProcessing = false;
        }, 300);
    });
}

document.addEventListener('DOMContentLoaded', renderWeekCalendar);