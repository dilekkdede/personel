const API = process.env.API_URL || 'http://localhost:18080/rest/api';
const USERNAME = process.env.SEED_USER || 'admin';
const PASSWORD = process.env.SEED_PASSWORD || 'Admin123!';

const CITIES = [
  ['İstanbul', '34'], ['Ankara', '06'], ['İzmir', '35'], ['Bursa', '16'],
  ['Antalya', '07'], ['Adana', '01'], ['Konya', '42'], ['Gaziantep', '27'],
  ['Kocaeli', '41'], ['Mersin', '33'], ['Diyarbakır', '21'], ['Kayseri', '38'],
  ['Eskişehir', '26'], ['Samsun', '55'], ['Trabzon', '61'], ['Malatya', '44'],
  ['Erzurum', '25'], ['Van', '65'], ['Denizli', '20'], ['Sakarya', '54']
];

const UNITS = [
  ['İnsan Kaynakları', 'IK'], ['Bilgi Teknolojileri', 'BT'], ['Muhasebe', 'MUH'],
  ['Satış', 'SAT'], ['Pazarlama', 'PZR'], ['Operasyon', 'OPS'], ['Hukuk', 'HUK'],
  ['Satın Alma', 'SA'], ['Ar-Ge', 'ARGE'], ['Üretim', 'URT'], ['Kalite', 'KLT'],
  ['Lojistik', 'LOJ'], ['Müşteri Destek', 'DEST'], ['Eğitim', 'EGT'],
  ['Finans', 'FIN'], ['Yönetim', 'YON'], ['Güvenlik', 'GUV'], ['İletişim', 'ILT']
];

const PEOPLE = [
  ['Ayşe', 'Yılmaz', 'İK Uzmanı'], ['Mehmet', 'Kaya', 'Yazılım Geliştirici'],
  ['Elif', 'Demir', 'Muhasebe Uzmanı'], ['Can', 'Şahin', 'Satış Temsilcisi'],
  ['Zeynep', 'Çelik', 'Pazarlama Uzmanı'], ['Emre', 'Yıldız', 'Operasyon Analisti'],
  ['Selin', 'Aydın', 'Hukuk Danışmanı'], ['Burak', 'Öztürk', 'Satın Alma Uzmanı'],
  ['Deniz', 'Arslan', 'Ar-Ge Mühendisi'], ['Merve', 'Koç', 'Üretim Sorumlusu'],
  ['Onur', 'Aslan', 'Kalite Uzmanı'], ['Ceren', 'Doğan', 'Lojistik Uzmanı'],
  ['Hakan', 'Polat', 'Destek Uzmanı'], ['İrem', 'Kurt', 'Eğitmen'],
  ['Barış', 'Aksoy', 'Finans Analisti'], ['Gizem', 'Erdoğan', 'Yönetici Asistanı'],
  ['Tolga', 'Çetin', 'Güvenlik Sorumlusu'], ['Pınar', 'Acar', 'İletişim Uzmanı'],
  ['Kerem', 'Bozkurt', 'Proje Yöneticisi'], ['Seda', 'Güneş', 'İK İşe Alım']
];

let token = '';

async function call(method, path, body) {
  const response = await fetch(API + path, {
    method,
    headers: {
      'Content-Type': 'application/json',
      ...(token ? {Authorization: 'Bearer ' + token} : {})
    },
    body: body ? JSON.stringify(body) : undefined
  });
  return response.json();
}

function ok(body) {
  return body && (body.status === 200 || body.status === 201);
}

async function main() {
  const login = await call('POST', '/auth/login', {username: USERNAME, password: PASSWORD});
  if (!ok(login) || !login.data?.token) {
    throw new Error('Giriş başarısız: ' + (login.message || JSON.stringify(login)));
  }
  token = login.data.token;

  const existingCities = (await call('GET', '/city/get-all')).data || [];
  const cityByName = new Map(existingCities.map(c => [c.name, c]));
  for (const [name, code] of CITIES) {
    if (cityByName.has(name)) continue;
    const saved = await call('POST', '/city/save', {name, code});
    if (!ok(saved)) throw new Error('Şehir eklenemedi ' + name + ': ' + saved.message);
    cityByName.set(name, saved.data);
  }

  const existingUnits = (await call('GET', '/unit/get-all')).data || [];
  const unitByCode = new Map(existingUnits.map(u => [u.code, u]));
  for (const [name, code] of UNITS) {
    if (unitByCode.has(code)) continue;
    const saved = await call('POST', '/unit/save', {name, code});
    if (!ok(saved)) throw new Error('Birim eklenemedi ' + name + ': ' + saved.message);
    unitByCode.set(code, saved.data);
  }

  const cities = [...cityByName.values()];
  const units = [...unitByCode.values()];
  const personelPage = await call('GET', '/personel/list?page=0&size=200');
  const existingPeople = personelPage.data?.content || [];
  const userNames = new Set(existingPeople.map(p => p.userName).filter(Boolean));

  let createdPeople = 0;
  for (let i = 0; i < PEOPLE.length; i++) {
    const [firstName, lastName, bolum] = PEOPLE[i];
    const userName = ('demo_' + firstName + lastName).toLowerCase()
      .replaceAll('ç', 'c').replaceAll('ğ', 'g').replaceAll('ı', 'i')
      .replaceAll('ö', 'o').replaceAll('ş', 's').replaceAll('ü', 'u');
    if (userNames.has(userName)) continue;

    const city = cities[i % cities.length];
    const unit = units[i % units.length];
    const saved = await call('POST', '/personel/save', {
      firstName,
      lastName,
      userName,
      bolum,
      description: bolum + ' olarak görev yapıyor',
      birthDate: `199${i % 10}-0${(i % 9) + 1}-15`,
      city: {id: city.id},
      unit: {id: unit.id},
      adres: {description: `${city.name} Merkez Mah. No:${10 + i}`}
    });
    if (!ok(saved)) throw new Error('Personel eklenemedi ' + firstName + ': ' + saved.message);

    const personelId = saved.data.id;
    await call('POST', '/contact/save', {
      personelId,
      type: 'EMAIL',
      contact: userName + '@personel.local'
    });
    await call('POST', '/contact/save', {
      personelId,
      type: 'TELEFON',
      contact: '0532' + String(1000000 + i).slice(-7)
    });
    createdPeople += 1;
  }

  const cityCount = (await call('GET', '/city/get-all')).data?.length || 0;
  const unitCount = (await call('GET', '/unit/get-all')).data?.length || 0;
  const peopleCount = (await call('GET', '/personel/list?page=0&size=1')).data?.totalElements || 0;
  console.log(`Hazır. Şehir: ${cityCount}, Birim: ${unitCount}, Personel: ${peopleCount} (yeni personel: ${createdPeople})`);
}

main().catch(error => {
  console.error(error.message || error);
  process.exit(1);
});
