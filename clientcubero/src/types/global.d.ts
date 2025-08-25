

interface IAppContext{
    userInfo:UserInfo;
    jwt:string;
    login(data:LoginDto):void;
    logout():void;
}

interface UserInfo{
    username:string;
    nickname:string;
}

interface LoginDto{
    username:string;
    password:string;
}

interface PreSolve{
    miliseconds:number;
    algoritm:string;
}

interface ISolve extends PreSolve{
    id:string;
}

interface PreFinalResults{
    max:number;
    min:number;
    media:number;
    avg5:number;
}

interface FinalResults extends PreFinalResults{
    solves:ISolve[];
    id:number;
    date:string;  
}

interface FinalResultGraph{
    date:string;
    avg5:number;
    media:number;
}
