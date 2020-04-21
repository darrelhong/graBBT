export class Promo {
    promoId: number
    promoCode: string
    value: number
    isActive: boolean
    
    constructor(promoId?: number, promoCode?: string, value?: number, isActive?: boolean)
    {
        this.promoId = promoId
        this.promoCode = promoCode
        this.value = value
        this.isActive = isActive
    }
}
