export const logstorage = {
    read():string{
        const getlog = localStorage.log;
        if(!getlog){
            localStorage.log = "";
        }
        return localStorage.log;
    },
    save(jwt:string){
        localStorage.log = jwt;
    }
}