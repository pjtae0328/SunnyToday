'use strict'

/* 슬라이더 */
const sliderContents = document.querySelector('.slider__contents');
const scheduleContents = document.querySelector('.schedule-cards');
let pressed;
let startX;
let scrollLeft;

function scrollEnd() {
    pressed = false;
    this.classList.remove('active');
}

function scrollStart(e) {
    pressed = true;
    this.classList.add('active');
    startX = e.pageX || e.touches[0].pageX - this.offsetLeft;
    scrollLeft = this.scrollLeft;
}

function scrollMove(e) {
    if (!pressed) return;

    e.preventDefault();
    const x = e.pageX || e.touches[0].pageX - this.offsetLeft;
    const dist = (x - startX);
    this.scrollLeft = scrollLeft - dist;
}

sliderContents.addEventListener('mousedown', scrollStart);
scheduleContents.addEventListener('mousedown', scrollStart);
sliderContents.addEventListener('touchstart', scrollStart);
scheduleContents.addEventListener('touchstart', scrollStart);

sliderContents.addEventListener('mousemove', scrollMove);
scheduleContents.addEventListener('mousemove', scrollMove);
sliderContents.addEventListener('touchmove', scrollMove);
scheduleContents.addEventListener('touchmove', scrollMove);

sliderContents.addEventListener('mouseleave', scrollEnd);
scheduleContents.addEventListener('mouseleave', scrollEnd);
sliderContents.addEventListener('mouseup', scrollEnd);
scheduleContents.addEventListener('mouseup', scrollEnd);
sliderContents.addEventListener('touchend', scrollEnd);
scheduleContents.addEventListener('touchend', scrollEnd);