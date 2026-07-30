package ports

import (
	"context"

	"github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/domain"
)

type InventoryRepository interface {
	Create(ctx context.Context, productID int64) error
    FindByID(ctx context.Context, productID int64) (*domain.Inventory, error)
    AddStock(ctx context.Context, productID int64, quantity int64) error
}