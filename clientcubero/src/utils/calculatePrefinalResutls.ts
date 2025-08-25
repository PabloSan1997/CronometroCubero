

const calculateMedia=(data:number[]):number=>{
    let res = 0;
    if(data.length === 0) return 0;
    for(let i of data){
        res += i;
    }
    if(data.length === 0) return 0;
    return Math.round(res/data.length);
}

export const calculatePrefinalResutls = (solves:PreSolve[]):PreFinalResults => {
   if(solves.length === 0) return {max:0, min:0, media:0, avg5:0};
    const milisecondsArray = [...solves.map(s => s.miliseconds)];
    milisecondsArray.sort((a, b) => a - b);
    const media = calculateMedia(milisecondsArray);
    const min = milisecondsArray.shift() || 0;
    const max = milisecondsArray.pop() || 0;
    const avg5 = calculateMedia(milisecondsArray);
    return {max, min, media, avg5};
}

