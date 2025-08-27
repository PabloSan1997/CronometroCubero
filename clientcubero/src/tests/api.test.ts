import { userapi } from '../api/userapi';
import { describe, expect, it } from 'vitest';

const userdeprueba = {
    username:'usertest',
    nickname:'Soy un test',
    password:'hola123'
}

const tokentest = 'eyJhbGciOiJIUzM4NCJ9.eyJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwibmlja25hbWUiOiJTb3kgdW4gdGVzdCIsInN1YiI6InVzZXJ0ZXN0IiwiaWF0IjoxNzU2MjUzMzM5LCJleHAiOjE3NTYzMzk3Mzl9.tvK9wt9vihcKH8RGRA_CIF8EnToeH6nM_o9BKmsepTwsYpvpc-Ym5YTPZv7PvnIS';


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
})