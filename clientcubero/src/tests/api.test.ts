import { cronoapi } from '../api/cronoapi';
import { userapi } from '../api/userapi';
import { describe, expect, it } from 'vitest';

const userdeprueba = {
    username:'soytest',
    nickname:'Soy un test',
    password:'hola123'
}

const tokentest = 'eyJhbGciOiJIUzM4NCJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwibmlja25hbWUiOiJTb3kgdW4gdGVzdCIsInN1YiI6InNveXRlc3QiLCJpYXQiOjE3NTYzMzIxMzEsImV4cCI6MTc1NjQxODUzMX0.4V_GyFZDYVVo8cRIiT9keL126SANMR2MLL4_CA6m87bAI_9jfa9rY6q18PcoKQF8';


describe('Pruebas para comunicar con el servidor', ()=>{
    describe('Pureba para el servicio que tiene que ver con el usuario y su informaicon', ()=>{
        it('login', async ()=>{
            const {username, password} = userdeprueba;
            const res = await userapi.login({username, password});
            expect(res).toMatchObject({
                jwt: expect.any(String)
            });
        });
        it('user info', async()=>{
            const userinfo = await userapi.getUserInfo(tokentest);
            expect(userinfo).toEqual({username:userdeprueba.username, nickname:userdeprueba.nickname});
        });
        it('login error message', async ()=>{
            try {
                await userapi.login({username:'nada', password:'nada'});
            } catch (error) {
                const err = error as ErrorDto;
                expect(err.message).toBe("Username o password incorrectos");
            }
        })
    });
    describe('Prueba con el servidor para todas las solicitudes para trabajar con la informacion de los tiempos', ()=>{
        it('Busar resultados por date, best y worst',async ()=>{
            const datos1 = cronoapi.findSolves(tokentest, 'best');
            const datos2 = cronoapi.findSolves(tokentest, 'date');
            const datos3 = cronoapi.findSolves(tokentest, 'worst');
            expect((await datos1).length).toBeGreaterThan(0)
            expect((await datos2).length).toBeGreaterThan(0)
            expect((await datos3).length).toBeGreaterThan(0)
        });
        it('Ver que los valores para graficar lleguen de la api',async ()=>{
            const data = await cronoapi.findGraphValues(tokentest);
            expect(data.length).toBeGreaterThan(0);
        })
    });
})