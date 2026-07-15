import './style.css'
import axios from 'axios';

const api = axios.create({
  baseURL: 'http://127.0.0.1:8081'
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    
    // Lista de rotas que NÃO devem receber o token
    // Adicionadas as rotas de listagem de carros, que são públicas
    // (o usuário pode navegar e ver os carros disponíveis sem estar logado;
    // o login só é exigido no momento de criar a reserva).
    const publicRoutes = [
        '/auth/login',
        '/usuarios',
        '/carros-disponiveis',
        '/carros/disponiveis'
    ];
    
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

    // Mostra ao usuário, sempre em pt-BR, a data que foi de fato registrada no campo,
    // já que o input type="date" nativo exibe o formato conforme o idioma do
    // navegador/SO (podendo ser MM/DD/AAAA), o que causa confusão para quem digita
    // pensando em DD/MM/AAAA.
    const atualizarConfirmacaoData = (inputId, spanId) => {
        const input = document.getElementById(inputId);
        const span = document.getElementById(spanId);
        if (!input || !span) return;
        if (!input.value) {
            span.textContent = '';
            return;
        }
        const data = new Date(input.value + 'T00:00:00');
        span.textContent = `Selecionado: ${data.toLocaleDateString('pt-BR')}`;
    };

    document.getElementById('data-inicio')?.addEventListener('change', () =>
        atualizarConfirmacaoData('data-inicio', 'data-inicio-confirmacao'));
    document.getElementById('data-fim')?.addEventListener('change', () =>
        atualizarConfirmacaoData('data-fim', 'data-fim-confirmacao'));

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

// Exemplo de serviço de reserva
const reservaService = {
  async listarDisponiveis(inicio, fim) {
    const response = await api.get(`/carros/disponiveis`, {
      params: { inicio, fim }
    });
    return response.data;
  },

  async criarReserva(dadosReserva) {
    return await api.post('/reservas', dadosReserva);
  }
};

async function verificarDisponibilidade() {
    const dataInicio = document.getElementById('data-inicio')?.value;
    const dataFim = document.getElementById('data-fim')?.value;
    const container = document.getElementById('lista-carros-resultado');

    if (!dataInicio || !dataFim) {
        alert("Por favor, selecione as datas de início e fim.");
        return;
    }

    if (new Date(dataFim) <= new Date(dataInicio)) {
        alert("A data de fim deve ser posterior à data de início.");
        return;
    }

    try {
        const carros = await reservaService.listarDisponiveis(dataInicio, dataFim);
        renderizarListaCarros(carros, dataInicio, dataFim);
    } catch (error) {
        console.error(error);
        alert("Erro ao buscar disponibilidade: " + (error.response?.data?.message || error.message));
    }
}

function renderizarListaCarros(carros, inicio, fim) {
    const container = document.getElementById('lista-carros-resultado');
    if (!container) return;

    if (carros.length === 0) {
        container.innerHTML = '<p class="text-center col-span-3 text-gray-500">Nenhum carro disponível para este período.</p>';
        return;
    }

    container.innerHTML = carros.map(carro => `
        <div class="border rounded-lg p-4 shadow-sm hover:shadow-md transition">
            <img src="${carro.imagemUrl || 'default-car.jpg'}" class="w-full h-40 object-cover rounded mb-2">
            <h4 class="font-bold text-lg">${carro.modelo}</h4>
            <p class="text-sm text-gray-600">Categoria: ${carro.categoria?.descricao || 'N/A'}</p>
            <p class="text-blue-600 font-bold mt-2">R$ ${carro.valorDiaria}/dia</p>
            <button onclick="window.realizarReserva(${carro.id}, '${inicio}', '${fim}')" 
                    class="w-full mt-4 bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
                Reservar Agora
            </button>
        </div>
    `).join('');
}

// ID da filial usada como padrão para retirada/devolução, enquanto não há
// seleção de filial na tela de reserva.
const FILIAL_PADRAO_ID = 1;

// Exposta globalmente para o onclick do HTML
window.realizarReserva = async (idVeiculo, inicio, fim) => {
    if (!localStorage.getItem('token')) {
        alert("Você precisa estar logado para realizar uma reserva!");
        // Opcional: abrirModal();
        return;
    }

    try {
        await reservaService.criarReserva({
            carroId: idVeiculo,
            filialRetiradaId: FILIAL_PADRAO_ID,
            filialDevolucaoId: FILIAL_PADRAO_ID,
            dataInicioPrevista: inicio,
            dataFimPrevista: fim
        });
        alert("Reserva efetuada com sucesso!");
        verificarDisponibilidade(); // Atualiza a lista após reservar
    } catch (error) {
        console.error(error);
        alert("Erro ao reservar: " + (error.response?.data?.message || "Verifique sua conexão."));
    }
};

// Vinculação do botão de busca (garanta que o ID exista no HTML)
document.getElementById('btn-buscar')?.addEventListener('click', verificarDisponibilidade);