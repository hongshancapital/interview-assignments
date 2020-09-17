import SwiftUI

struct StockRow: View {

    let record: StockRecord

    var body: some View {
        HStack {
            VStack(alignment: .leading, spacing: 5) {
                Text(record.name)
                    .font(.headline)
                Text(availableDescription)
                    .font(.subheadline)
                    .foregroundColor(.gray)
            }
            Spacer()
            Text("$"+record.price)
                .font(.caption)
                .foregroundColor(color)
                .padding(.horizontal, 10)
                .padding(.vertical, 2)
                .background(color.opacity(0.2).cornerRadius(10))
        }
    }

    var availableDescription: String {
        record.isInStock ? "In-stock" : "Out-of-stock"
    }

    var color: Color {
        record.isInStock ? .blue : .gray
    }
}

struct StockRow_Previews: PreviewProvider {
    static var previews: some View {
        StockRow(record: StockRecord(name: "V11", isInStock: true, price: "599.99"))
    }
}
