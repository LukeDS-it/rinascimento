import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

export function initApp(module) {
    platformBrowserDynamic()
        .bootstrapModule(module)
        .catch(onError);
}

function onError(reason: any) {
    console.error(reason);
    document.getElementById('spinner').classList.add('invisible');
    document.getElementById('loading').classList.add('invisible');
    document.getElementById('error').classList.remove('invisible');
}