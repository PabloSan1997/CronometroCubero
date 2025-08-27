

interface IAppContext{
    userInfo:UserInfo;
    jwt:string;
    login(data:LoginDto):void;
    logout():void;
    message:string;
    setMessage(msg:string):void;
}

interface ErrorDto{
    statusCode:number;
    message:string;
    error:string;
    timestamp:string;
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
    id:string;
    createdAt:string;  
}

interface FinalResultGraph{
    createdAt:string;
    avg5:number;
    media:number;
}
