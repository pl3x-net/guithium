realRemoveAttr = Element.prototype.removeAttribute;
Element.prototype.removeAttribute = function(attr) {
    if (attr == 'href') {
        return this.setAttribute(attr, '#');
    }
    realRemoveAttr(attr);
}
