package postgres

import (
	"context"
	"database/sql"
	"errors"

	"github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/domain"
	ports "github.com/lucas-rech/ms-ecommerce/inventory-service/internal/core/ports/out"
)

type inventoryRepositoryAdapter struct {
	db *sql.DB
}

func NewInventoryRepositoryAdapter(db *sql.DB) ports.InventoryRepository {
	return &inventoryRepositoryAdapter{
		db: db,
	}
}


func (i *inventoryRepositoryAdapter) Create(ctx context.Context, productID int64) error {
	query := `INSERT  INTO public.ESTOQUE(ID_PRODUTO) VALUES($1)`

    _, err := i.db.ExecContext(ctx, query, productID)
    return err
}

func (i *inventoryRepositoryAdapter) FindByID(ctx context.Context, productID int64) (*domain.Inventory, error) {
    query := `SELECT ID_PRODUTO, QTD_TOTAL, QTD_RESERVADA FROM public.ESTOQUE WHERE ID_PRODUTO = $1`

    row := i.db.QueryRowContext(ctx, query, productID)

    var e domain.Inventory
    err := row.Scan(&e.ProductId, &e.TotalAmount, &e.ReservedQuantity)
    if err != nil {
        if errors.Is(err, sql.ErrNoRows) {
            return nil, errors.New("inventory not found for this product")
        }
        return nil, err
    }

    return &e, err
}

func (i *inventoryRepositoryAdapter) AddStock(ctx context.Context, productID int64, quantity int64) error {
	query := `UPDATE public.ESTOQUE SET QTD_TOTAL = QTD_TOTAL + $1 WHERE ID_PRODUTO = $2`

    res, err := i.db.ExecContext(ctx, query, quantity, productID)
    if err != nil {
        return err
    }

    rowsAffected, err := res.RowsAffected()
    if err != nil {
        return err
    }
    if rowsAffected == 0 {
        return errors.New("no rows updated; product is not in stock")
    }

    return nil
}


