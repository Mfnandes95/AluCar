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

//Slider da seção de hero

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

  // JS para o carrossel de frota

  (function() {
    const track = document.getElementById('fleet-track');
    const cards = Array.from(document.querySelectorAll('.fleet-card'));
    const prevBtn = document.getElementById('prev-fleet');
    const nextBtn = document.getElementById('next-fleet');
    
    let currentIndex = 0;

    // Função principal que calcula e desenha o carrossel
    function updateCarousel() {
      if (!cards.length || !track) return;

      // Importante: Força o navegador a recalcular as larguras reais no momento
      const cardWidth = cards[0].offsetWidth;
      const containerWidth = track.parentElement.offsetWidth;

      // 1. Calcula a translação exata para centralizar o card ativo (currentIndex)
      const targetTranslate = (containerWidth / 2) - (cardWidth * currentIndex) - (cardWidth / 2);
      
      // 2. Aplica a translação no Track
      track.style.transform = `translateX(${targetTranslate}px)`;

      // 3. Aplica o efeito visual (Focus/Blur/Scale)
      cards.forEach((card, idx) => {
        if (idx === currentIndex) {
          card.style.opacity = '1';
          card.style.transform = 'scale(1.05)'; // Levemente maior
          card.style.filter = 'blur(0px)';
          card.style.zIndex = '10'; // Fica por cima
        } else {
          card.style.opacity = '0.4'; // Bem opaco
          card.style.transform = 'scale(0.88)'; // Levemente menor
          card.style.filter = 'blur(2px)'; // Leve desfoque
          card.style.zIndex = '1';
        }
      });
    }

    // Navegação
    nextBtn.addEventListener('click', () => {
      currentIndex = (currentIndex + 1) % cards.length;
      updateCarousel();
    });

    prevBtn.addEventListener('click', () => {
      currentIndex = (currentIndex - 1 + cards.length) % cards.length;
      updateCarousel();
    });

    // Clicar no card desfocado para centralizar
    cards.forEach((card, idx) => {
      card.addEventListener('click', () => {
        if (currentIndex !== idx) {
          currentIndex = idx;
          updateCarousel();
        }
      });
    });

    // --- A CORREÇÃO ESTÁ AQUI ---
    // Escuta o evento de redimensionamento da janela
    window.addEventListener('resize', () => {
      // Executa a função de atualização para refazer os cálculos de centralização
      updateCarousel();
    });

    // Garante que rodará correto na primeira vez que a página abrir
    // (Usa setTimeout para garantir que o CSS e imagens carregaram)
    if (document.readyState === 'complete') {
      updateCarousel();
    } else {
      window.addEventListener('load', updateCarousel);
    }
  })();


  // JS para o flip card da seção de lojas
  document.querySelectorAll('.flip-card').forEach(card => {
    card.addEventListener('click', function(e) {
      // Se o usuário clicar diretamente no botão de "Selecionar esta Loja", não desvira o card
      if (e.target.tagName === 'A' || e.target.closest('a')) return;

      const inner = this.querySelector('.flip-card-inner');
      inner.classList.toggle('rotate-y-180');
    });
  });


  // JS para o FAQ
  document.querySelectorAll('.faq-trigger').forEach(trigger => {
    trigger.addEventListener('click', () => {
      const parent = trigger.parentElement;
      const content = parent.querySelector('.faq-content');
      const icon = trigger.querySelector('.faq-icon');
      
      const isOpen = !content.classList.contains('hidden');

      // Fecha todos os outros itens abertos (opcional, para manter organizado)
      document.querySelectorAll('.faq-content').forEach(el => el.classList.add('hidden'));
      document.querySelectorAll('.faq-icon').forEach(el => {
        el.textContent = '+';
        el.classList.remove('rotate-45');
      });

      // Se não estava aberto, abre o item atual
      if (!isOpen) {
        content.classList.remove('hidden');
        icon.textContent = '×';
        icon.classList.add('rotate-45');
      }
    });
  });


// Configuração da API do Alucar na Render
const API_URL = 'https://alucar-backend.onrender.com'; // Cole a URL gerada pela Render aqui

async function carregarCatalogo() {
  const gridCarros = document.querySelector('#grid-carros'); // Vamos precisar dessa ID no seu HTML
  
  if (!gridCarros) return; // Só executa se a seção existir na página atual

  try {
    gridCarros.innerHTML = '<p class="text-zinc-400 text-center col-span-full">Carregando frota...</p>';

    const response = await fetch(`${API_URL}/api/veiculos`);
    if (!response.ok) throw new Error('Erro ao buscar veículos');
    
    const carros = await response.json();
    
    // Limpa o "Carregando..."
    gridCarros.innerHTML = '';

    if (carros.length === 0) {
      gridCarros.innerHTML = '<p class="text-zinc-400 text-center col-span-full">Nenhum carro disponível no momento.</p>';
      return;
    }

    // Renderiza cada carro na grid com as suas classes Tailwind
    carros.forEach(carro => {
      const card = document.createElement('div');
      card.className = 'bg-white rounded-xl shadow-md overflow-hidden border border-zinc-100 p-4 transition-transform hover:scale-[1.02]';
      
      card.innerHTML = `
        <img 
          src="${carro.imagemUrl || 'https://via.placeholder.com/300x180'}" 
          alt="${carro.modelo}" 
          class="w-full h-40 object-cover rounded-lg mb-4"
        />
        <h3 class="text-lg font-semibold text-zinc-900">${carro.modelo}</h3>
        <p class="text-sm text-zinc-500 mb-4">${carro.marca} • ${carro.ano}</p>
        <div class="flex justify-between items-center">
          <span class="text-emerald-600 font-bold">R$ ${carro.precoDiaria}/dia</span>
          <button class="bg-zinc-900 text-white px-4 py-2 rounded-lg text-sm font-medium hover:bg-zinc-800 transition-colors">
            Reservar
          </button>
        </div>
      `;
      
      gridCarros.appendChild(card);
    });

  } catch (error) {
    console.error('Erro na conexão com o backend:', error);
    gridCarros.innerHTML = '<p class="text-red-500 text-center col-span-full">Ops! Não foi possível carregar o catálogo de carros.</p>';
  }
}

// Dispara a busca quando a página carregar
document.addEventListener('DOMContentLoaded', carregarCatalogo);

//API BUSCA

// 1. Defina a URL do seu backend que está rodando na Render


// 2. Função para buscar os dados
async function carregarDados() {
  try {
    const response = await fetch(`${API_URL}/api/carros`); // Ajuste a rota se for outra
    if (!response.ok) throw new Error('Erro na rede');
    
    const dados = await response.json();
    console.log('Dados recebidos do banco:', dados);
    
    // Aqui você adicionaria o código para exibir os dados na tela (ex: document.getElementById('...').innerHTML = ...)
  } catch (error) {
    console.error('Falha ao conectar com o backend:', error);
  }
}

// 3. Executa a função assim que a página carregar
document.addEventListener('DOMContentLoaded', carregarDados);