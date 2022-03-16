//
//  EmptyDataPlaceholder.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/11.
//

import SwiftUI

struct EmptyDataPlaceholder: View {
    var title: String = "No data."
    
    var body: some View {
        Text(title)
            .foregroundColor(Color.h8f8e94)
    }
}

struct EmptyDataPlaceholder_Previews: PreviewProvider {
    static var previews: some View {
        EmptyDataPlaceholder()
    }
}
