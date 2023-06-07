//
//  ProductRow.swift
//  ProductHunt
//
//  Created by Jinya on 2022/10/6.
//

import SwiftUI

struct ProductRow: View {
    @Binding var product: Product

    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            ArtworkImage(url: product.artworkURL)
                .frame(width: 60, height: 60)
            VStack(alignment: .leading, spacing: 6) {
                Text(product.trackName)
                    .lineLimit(1)
                    .font(.headline)
                Text(product.description)
                    .lineLimit(2)
                    .font(.subheadline)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            LikeButton(isLiked: $product.isLiked)
        }
        .padding(.vertical, 16)
        .padding(.horizontal, 18)
        .background(Color(uiColor: .secondarySystemGroupedBackground))
        .clipShape(
            RoundedRectangle(cornerRadius: 10, style: .continuous)
        )
    }
}

#if DEBUG
struct ProductRow_Previews: PreviewProvider {
    static var previews: some View {
        HStack {
            ProductRow(product: .constant(Product.preview))
        }
        .padding(.all, 20)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
#endif
