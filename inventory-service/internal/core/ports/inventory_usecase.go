package ports

import (
	"context"

	"github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/domain"
)

type InventoryService interface {
	Create(ctx context.Context, productID int64) error
    FindProductStock(ctx context.Context, productID int64) (*domain.Inventory, error)
    GetBalance(ctx context.Context, productID int64) (int64, error)
    AddStock(ctx context.Context, productID, quantity int64) error
}