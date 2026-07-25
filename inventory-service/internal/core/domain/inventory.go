package domain

type Inventory struct {
	ProductId        int64
	TotalAmount      int64
	ReservedQuantity int64
}

func (i *Inventory) GetAvailableBalance() int64 {
	return i.TotalAmount - i.ReservedQuantity;
}

