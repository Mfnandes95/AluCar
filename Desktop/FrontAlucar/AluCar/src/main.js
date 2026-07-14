import './style.css'
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://127.0.0.1:8081'
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    
    // Lista de rotas que NÃO devem receber o token
    const publicRoutes = ['/auth/login', '/usuarios'];
    
    // Verifica se a URL da requisição inclui alguma das rotas públicas
    const isPublic = publicRoutes.some(route => config.url.includes(route));

    if (token && !isPublic) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

let currentSlide = 0;
let totalSlides = 0;
let autoSlideInterval;

document.addEventListener('DOMContentLoaded', () => {
    const menuBtn = document.getElementById('menu-btn');
    const mobileMenu = document.getElementById('mobile-menu');
    const modal = document.getElementById('login-modal');
    const btnEntrar = document.getElementById('btn-acao');
    const toggleBtn = document.getElementById('toggle-form');
    let isLogin = true;

    if (menuBtn && mobileMenu) {
        menuBtn.addEventListener('click', () => mobileMenu.classList.toggle('hidden'));
    }

    const abrirModal = () => modal?.classList.remove('hidden');
    const fecharModal = () => modal?.classList.add('hidden');

    verificarEstadoUsuario();

    document.getElementById('btn-abrir-login')?.addEventListener('click', () => {
        if (localStorage.getItem('token')) {
            alert("Redirecionando para o seu perfil...");
        } else {
            abrirModal();
        }
    });
    
    document.getElementById('btn-abrir-cadastro')?.addEventListener('click', abrirModal);
    document.getElementById('close-modal')?.addEventListener('click', fecharModal);

    toggleBtn?.addEventListener('click', () => {
        isLogin = !isLogin;
        document.getElementById('modal-title').innerText = isLogin ? "Login AluCar" : "Criar conta";
        btnEntrar.innerText = isLogin ? "Entrar" : "Cadastrar";
        document.getElementById('texto-alternar').innerText = isLogin ? "Não tem conta?" : "Já tem conta?";
        toggleBtn.innerText = isLogin ? "Cadastre-se" : "Entrar";

        const campos = ['nome', 'cpf', 'telefone', 'cnh', 'endereco'];
        campos.forEach(id => {
            document.getElementById(id)?.classList.toggle('hidden', isLogin);
        });
    });

    btnEntrar?.addEventListener('click', async () => {
        const getVal = (id) => document.getElementById(id)?.value || "";
        const dados = {
            nome: getVal('nome'),
            email: getVal('email'),
            senha: getVal('senha'),
            cpf: getVal('cpf'),
            telefone: getVal('telefone'),
            cnh: getVal('cnh'),
            endereco: getVal('endereco')
        };

        try {
            if (isLogin) {
                const res = await api.post('/auth/login', { email: dados.email, senha: dados.senha });
                localStorage.setItem('token', res.data.token);
                alert('Bem-vindo à AluCar!');
                fecharModal();
                verificarEstadoUsuario();
            } else {
                await api.post('/usuarios', dados);
                alert('Cadastro realizado! Faça login para continuar.');
                toggleBtn.click();
            }
        } catch (err) {
            console.error(err);
            alert('Erro: ' + (err.response?.data?.message || 'Falha na operação.'));
        }
    });

    carregarCarros();
});

// --- FUNÇÕES DE ESTADO E SLIDER ---

function verificarEstadoUsuario() {
    const token = localStorage.getItem('token');
    const btnEntrarHeader = document.getElementById('btn-abrir-login');
    if (btnEntrarHeader) {
        btnEntrarHeader.innerText = token ? "Meu Perfil" : "Entrar";
    }
}

function updateSlider() {
  const container = document.getElementById('slider-container');
  if(!container) return;
  container.style.transform = `translateX(-${currentSlide * 100}%)`;
  
  for (let i = 0; i < totalSlides; i++) {
    const dot = document.getElementById(`dot-${i}`);
    if (dot) {
      dot.className = `w-8 h-1.5 rounded-full transition-all duration-300 ${i === currentSlide ? 'bg-white' : 'bg-white/40'}`;
    }
  }
}

window.goToSlide = (index) => {
  currentSlide = index;
  updateSlider();
  iniciarAutoSlide();
};

function iniciarAutoSlide() {
  clearInterval(autoSlideInterval);
  autoSlideInterval = setInterval(() => {
    currentSlide = (currentSlide + 1) % totalSlides;
    updateSlider();
  }, 4000);
}

function criarSlideHTML(carro) {
  return `
    <div class="min-w-full flex-shrink-0 grid grid-cols-1 md:grid-cols-2 items-center p-6 sm:p-10 lg:p-12 gap-6 min-h-[320px]">
      <div class="flex flex-col justify-center space-y-3 text-white z-10">
        <h3 class="text-2xl sm:text-3xl lg:text-4xl font-extrabold uppercase tracking-tight leading-tight">
          ${carro.modelo}
        </h3>
        <p class="text-sm sm:text-base text-gray-300">A partir de R$ ${carro.valorDiaria}/dia</p>
      </div>
      <div class="flex justify-center items-center">
        <img src="${carro.imagemUrl}" alt="${carro.modelo}" class="max-h-64 w-auto object-contain drop-shadow-2xl" />
      </div>
    </div>
  `;
}

async function carregarCarros() {
  const container = document.getElementById('slider-container');
  const dotsContainer = document.getElementById('slider-dots');
  
  try {
    // Alterado de .post para .get com query params
    const response = await api.get('/carros-disponiveis', {
      params: { pagina: 0, tamanho: 5 }
    });

    console.log("Estrutura da resposta:", response.data);
    
    const carros = response.data;
    console.log("Carros recebidos:", carros)

    if (!carros || carros.length === 0) {
      console.warn("Nenhum carro disponível encontrado.");
      return;
    }

    container.innerHTML = carros.map(criarSlideHTML).join('');
    totalSlides = carros.length;

    if (dotsContainer) {
      dotsContainer.innerHTML = Array.from({length: totalSlides}, (_, i) => 
        `<button id="dot-${i}" onclick="goToSlide(${i})" class="w-8 h-1.5 rounded-full ${i === 0 ? 'bg-white' : 'bg-white/40'}"></button>`
      ).join('');
    }
    iniciarAutoSlide();
  } catch (error) {
    console.error('Erro ao carregar carros:', error);
  }
}