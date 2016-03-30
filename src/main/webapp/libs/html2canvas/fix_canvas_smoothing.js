var oldgetContext = HTMLCanvasElement.prototype.getContext;
function getSmoothContext(contextType) {
  var resCtx = oldgetContext.apply(this, arguments);
  if (contextType == '2d') {
   setToFalse(resCtx, 'imageSmoothingEnabled');
   setToFalse(resCtx, 'mozImageSmoothingEnabled');
   setToFalse(resCtx, 'oImageSmoothingEnabled');
   setToFalse(resCtx, 'webkitImageSmoothingEnabled');  
  }
  return resCtx ;  
}
function setToFalse(obj, prop) { if ( obj[prop] !== undefined ) obj[prop] = false; }
HTMLCanvasElement.prototype.getContext = getSmoothContext ;
