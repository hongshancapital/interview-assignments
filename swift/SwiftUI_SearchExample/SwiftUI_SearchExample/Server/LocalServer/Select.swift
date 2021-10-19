import Foundation

/// 构建我们需要的数据
func buildSearchListData() -> [SearchModel] {
    return [
        SearchModel(title: "Vacuum", items: buildVacuumItems()),
        SearchModel(title: "Hair Dryer", items: buildHairDryer())
    ]
}

func buildVacuumItems() -> [SearchItemModel] {
    return [
        SearchItemModel(name: "V11", stock: .instock, price: 599.99),
        SearchItemModel(name: "V10", stock: .outstock, price: 399.99)
    ]
}

func buildHairDryer() -> [SearchItemModel] {
    return [
        SearchItemModel(name: "Supersonic", stock: .instock, price: 399.99),
    ]
}
