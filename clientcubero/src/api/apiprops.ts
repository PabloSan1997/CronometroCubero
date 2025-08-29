
const vieworigin = import.meta.env.DEV?"http://localhost:3008":window.location.origin;


export const apiprops = {
    baseUrl:`${vieworigin}/api`,
    baseSocket:`${vieworigin}/cronoconnect`
}