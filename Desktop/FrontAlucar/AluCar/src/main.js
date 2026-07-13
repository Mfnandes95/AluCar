import './style.css'

/*MENU SANDUÍCHE RESPONSIVO*/
document.addEventListener('DOMContentLoaded', () => {
  const menuBtn = document.getElementById('menu-btn');
  const mobileMenu = document.getElementById('mobile-menu');

  if (menuBtn && mobileMenu) {
    menuBtn.addEventListener('click', () => {
      mobileMenu.classList.toggle('hidden');
    });

    // Fecha o menu mobile ao clicar em qualquer link dele
    const mobileLinks = mobileMenu.querySelectorAll('a');
    mobileLinks.forEach(link => {
      link.addEventListener('click', () => {
        mobileMenu.classList.add('hidden');
      });
    });
  }
});

let currentSlide = 0;
  const totalSlides = 2;
  const container = document.getElementById('slider-container');

  function updateSlider() {
    container.style.transform = `translateX(-${currentSlide * 100}%)`;
    
    // Atualiza o estado dos traços/indicadores
    for (let i = 0; i < totalSlides; i++) {
      const dot = document.getElementById(`dot-${i}`);
      if (i === currentSlide) {
        dot.classList.remove('bg-white/40');
        dot.classList.add('bg-white');
      } else {
        dot.classList.remove('bg-white');
        dot.classList.add('bg-white/40');
      }
    }
  }

  function goToSlide(index) {
    currentSlide = index;
    updateSlider();
  }

  // Troca automática a cada 4 segundos
  setInterval(() => {
    currentSlide = (currentSlide + 1) % totalSlides;
    updateSlider();
  }, 4000);

