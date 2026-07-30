package services

import (
	"context"
	"errors"

	"github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/domain"
	ports "github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/ports"
)

type inventoryService struct {
	r ports.InventoryRepository
}


func NewInventoryService(r ports.InventoryRepository) ports.InventoryService {
	return &inventoryService{
		r: r,
	}
}

func (i *inventoryService) Create(ctx context.Context, productID int64) error {
	if productID <= 0 {
		return errors.New("invalid product id: must be greater than 0")
	}

	err := i.r.Insert(ctx, productID)
	if err != nil {
		return err
	}

	return nil
}

func (i *inventoryService) FindProductStock(ctx context.Context, productID int64) (*domain.Inventory, error) {
	if productID <= 0 {
		return nil, errors.New("invalid product id: must be greater than 0")
	}

	inventory, err := i.r.FindByID(ctx, productID)
	if err != nil {
		return nil, err
	}

	return inventory, nil
}

func (i *inventoryService) GetBalance(ctx context.Context, productID int64) (int64, error) {
	if productID <= 0 {
		return 0, errors.New("invalid product id: must be greater than 0")
	}

    inventory, err := i.r.FindByID(ctx, productID)
    if err != nil {
        return 0, err
    }

    return inventory.GetAvailableBalance(), nil
}

func (i *inventoryService) AddStock(ctx context.Context, productID, quantity int64) error {
    if productID <= 0 {
		return errors.New("invalid product id: must be greater than 0")
	}

    err := i.r.AddStock(ctx, productID, quantity)
    if err != nil {
        return err
    }

    return nil
}
