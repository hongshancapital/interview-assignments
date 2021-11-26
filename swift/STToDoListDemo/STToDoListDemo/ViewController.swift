//
//  ViewController.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/23.
//

import UIKit
import SnapKit

class ViewController: UIViewController {
    
    private let defalultPlaceholder = "Add new"
    @IBOutlet weak var tableView: UITableView!
    private var selectIndexPath: IndexPath?
    private var createModel: STListModel = STListModel()
    private let viewModel: STListViewModel = STListViewModel()

    override func viewDidLoad() {
        super.viewDidLoad()
        
        self.tableView.register(STListTableViewCell.self, forCellReuseIdentifier: "STListTableViewCell")
        self.tableView.contentInsetAdjustmentBehavior = .never
        
        self.viewModel.addToDoList(title: "SwiftUI Essentials", content: "Building Lists and Navigation")
        self.viewModel.addToDoList(title: "SwiftUI Essentials", content: "Creating and Combining Views")
        self.viewModel.addToDoList(title: "SwiftUI Essentials", content: "Handling User Input")

        self.viewModel.addToDoList(title: "Drawing and Animation", content: "Animating Views and Transitions")
        self.viewModel.addToDoList(title: "Drawing and Animation", content: "Drawing Paths and Shapes")

        self.viewModel.addToDoList(title: "App Design and Layout", content: "Handling User Input")
    
        self.title = "List"
        self.definesPresentationContext = true
        self.navigationItem.hidesSearchBarWhenScrolling = true
        self.navigationItem.searchController = self.searchController
        self.view.addSubview(self.bottomView)
        self.bottomView.snp.makeConstraints { make in
            make.left.right.equalToSuperview()
            make.bottom.equalToSuperview().offset(-20)
            make.height.equalTo(50)
        }
        
        NotificationCenter.default.addObserver(self, selector:#selector(keyBoardWillShowNotification(notification:)), name: UIResponder.keyboardWillChangeFrameNotification, object: nil)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        self.tableView.reloadData()
    }
    
    @objc func itemClick(sender: STCircleButton) {
        if let indexPath = sender.indexPath {
            var model = self.viewModel.cellForRowAt(section: indexPath.section, row: indexPath.row, isFiltering: false)
            if model.isDeleted, model.isSelected { return }
            model.isSelected = !model.isSelected
            self.viewModel.didSelectRowAt(section: indexPath.section, row: indexPath.row, model: model)
            self.tableView.reloadData()
        }
    }
    
    func resetTextField() {
        self.textField.text = ""
        self.textField.originText = ""
        self.textField.type = .inputContent
        self.textField.placeholder = self.defalultPlaceholder
    }
    
    func showSelectView() {
        self.navigationController?.navigationBar.isHidden = true
        self.selectView.removeFromSuperview()
        self.view.addSubview(self.selectView)
        self.selectView.frame = CGRect.init(x: 0, y: UIScreen.main.bounds.size.height, width: UIScreen.main.bounds.size.width, height: UIScreen.main.bounds.size.height)
        UIView.animate(withDuration: 0.5) {
            self.selectView.frame = CGRect.init(x: 0, y: 0, width: UIScreen.main.bounds.size.width, height: UIScreen.main.bounds.size.height)
        };
    }
    
    func hiddenSelectView() {
        self.navigationController?.navigationBar.isHidden = false
        self.selectView.removeFromSuperview()
        self.view.addSubview(self.selectView)
        UIView.animate(withDuration: 0.5) {
            self.selectView.frame = CGRect.init(x: 0, y: UIScreen.main.bounds.size.height, width: UIScreen.main.bounds.size.width, height: UIScreen.main.bounds.size.height)
        } completion: { result in
            self.selectView.removeFromSuperview()
        };
        self.resetTextField()
    }
    
    @objc func keyBoardWillShowNotification(notification: NSNotification) {
        if let rect = notification.userInfo?[UIResponder.keyboardFrameEndUserInfoKey] as? CGRect {
            UIView.animate(withDuration: 0.3) {
                // 键盘高度
                var height = UIScreen.main.bounds.size.height - rect.origin.y
                if height < 20 {
                    height = 20
                }
                self.bottomView.snp.updateConstraints { make in
                    make.bottom.equalToSuperview().offset(-height)
                }
                let duration = notification.userInfo?[UIResponder.keyboardAnimationDurationUserInfoKey] as! TimeInterval
                UIView.animate(withDuration: duration) {
                    self.view.layoutIfNeeded()
                }
            };
        }
    }
    
    private lazy var searchController: UISearchController = {
        let searchController = UISearchController(searchResultsController: nil)
        searchController.obscuresBackgroundDuringPresentation = true
        searchController.searchBar.placeholder = "Search To Do Item"
        searchController.searchResultsUpdater = self
        searchController.delegate = self
        searchController.searchBar.sizeToFit()
        return searchController
    }()
    
    private lazy var bottomView: UIView = {
        let view = UIView()
        view.backgroundColor = STConstant.f6f6f6()
        view.addSubview(self.textField)
        self.textField.snp.makeConstraints { make in
            make.left.equalTo(20)
            make.right.equalTo(-20)
            make.bottom.height.equalToSuperview()
        }
        return view
    }()
    
    private lazy var textField: STTextField = {
        let textField = STTextField()
        textField.delegate = self
        textField.layer.cornerRadius = 10
        textField.textColor = UIColor.black
        textField.backgroundColor = UIColor.white
        textField.placeholder = defalultPlaceholder
        textField.font = UIFont.systemFont(ofSize: 20)
        textField.config(orginLeft: 15, orginRight: 10)
        textField.layer.borderColor = STConstant.titleColor().cgColor
        textField.layer.borderWidth = 1
        textField.translatesAutoresizingMaskIntoConstraints = false
        return textField
    }()
    
    private lazy var selectView: STSelectListView = {
        let view = STSelectListView()
        view.delegate = self
        view.layer.cornerRadius = 10
        view.backgroundColor = UIColor.black
        return view
    }()
}

extension ViewController: UISearchResultsUpdating, UISearchControllerDelegate {
    func updateSearchResults(for searchController: UISearchController) {
        filterContentForSearchText(searchController.searchBar.text!)
    }

    func searchBarIsEmpty() -> Bool {
        return searchController.searchBar.text?.isEmpty ?? true
    }

    func filterContentForSearchText(_ searchText: String, scope: String = "All") {
        self.viewModel.filteredModels(searchText: searchText.lowercased())
        self.tableView.reloadData()
    }
    
    func isFiltering() -> Bool {
        return searchController.isActive && !searchBarIsEmpty()
    }
}

extension ViewController: UITableViewDelegate, UITableViewDataSource {
    
    func scrollViewDidEndScrollingAnimation(_ scrollView: UIScrollView) {
        self.tableView.contentOffset = CGPoint.init(x: 0, y: floor(self.tableView.contentOffset.y))
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return self.viewModel.numberOfSections(isFiltering: isFiltering())
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.viewModel.numberOfRowsInSection(section: section, isFiltering: isFiltering())
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(withIdentifier: "STListTableViewCell", for: indexPath) as? STListTableViewCell {
            let model = self.viewModel.cellForRowAt(section: indexPath.section, row: indexPath.row, isFiltering: isFiltering())
            cell.update(isSelected: model.isSelected)
            cell.update(content: model.content, isDeleted: model.isDeleted)
            cell.bigCircleView.indexPath = indexPath
            cell.bigCircleView.addTarget(self, action: #selector(itemClick(sender:)), for: .touchUpInside)
            return cell
        }
        return UITableViewCell()
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let model = self.viewModel.cellForRowAt(section: indexPath.section, row: indexPath.row, isFiltering: isFiltering())
        if model.isDeleted, model.isSelected { return }
        self.selectIndexPath = indexPath
        self.textField.becomeFirstResponder()
        self.textField.text = model.content
        self.textField.originText = model.content
        self.createModel.title = model.title
        self.createModel.content = model.content
        self.textField.type = .modify
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        let headView = UIView()
        headView.backgroundColor = STConstant.f6f6f6()

        let titleLabel = UILabel()
        titleLabel.textColor = UIColor.black
        titleLabel.backgroundColor = UIColor.clear
        titleLabel.translatesAutoresizingMaskIntoConstraints = false
        titleLabel.font = UIFont.systemFont(ofSize: 20, weight: .semibold)
        
        headView.addSubview(titleLabel)
        titleLabel.snp.makeConstraints { make in
            make.left.equalTo(0)
            make.centerY.equalTo(headView.snp.centerY)
        }
        titleLabel.text = self.viewModel.viewForHeaderInSection(section: section)
        return headView
    }
}

extension ViewController: UITextFieldDelegate {
    
    func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        self.textField.type = .inputContent
        return true
    }
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        if let field = textField as? STTextField {
            switch(field.type) {
            case .inputContent:
                self.createModel.content = textField.text ?? ""
                break
            case .createGroup:
                self.createModel.title = textField.text ?? ""
                break
            case .selectGroup:
                self.createModel.title = textField.text ?? ""
                break
            case .modify:
                self.createModel.content = textField.text ?? ""
                break
            default:
                break
            }
            
            if textField.text?.count ?? 0 < 1 {
                if field.type == .selectGroup || field.type == .createGroup {
                    let alertVC = UIAlertController.init(title: "please input name", message: nil, preferredStyle: .alert)
                    let okAction = UIAlertAction.init(title: "知道了", style: .default) { action in }
                    alertVC.addAction(okAction)
                    self.navigationController?.present(alertVC, animated: true, completion: nil)
                } else if field.type == .inputContent || field.type == .modify {
                    if self.textField.originText.count > 0 {
                        // 删除 to do list
                        self.resetTextField()
                        self.deleteToDo(isDelete: true, indexPath: self.selectIndexPath ?? nil)
                    }
                }
            } else {
                if field.text == self.textField.originText { return }
                if field.type == .inputContent {
                    let alertVC = UIAlertController.init(title: "select an action", message: nil, preferredStyle: .actionSheet)
                    let addNewAction = UIAlertAction.init(title: "Create a new group", style: .default) { action in
                        self.textField.text = ""
                        self.textField.placeholder = "enter a new group name"
                        self.textField.becomeFirstResponder()
                        self.textField.type = .createGroup
                    }
                    let seleteAction = UIAlertAction.init(title: "Select a group", style: .default) { action in
                        self.textField.type = .selectGroup
                        self.textField.placeholder = "select a group"
                        self.showSelectView()
                    }
                    let cancelAction = UIAlertAction.init(title: "do nothing", style: .cancel) { action in
                        self.resetTextField()
                    }
                    alertVC.addAction(addNewAction)
                    alertVC.addAction(seleteAction)
                    alertVC.addAction(cancelAction)
                    self.navigationController?.present(alertVC, animated: true, completion: nil)
                } else if field.type == .selectGroup || field.type == .createGroup {
                    // 创建一个to do
                    if field.type == .selectGroup {
                        self.viewModel.addToDoList(title: self.createModel.title, content: self.createModel.content, isAdd: false, indexPath: self.selectIndexPath ?? nil)
                        STToastView.showToastView(str: "add success")
                    } else  {
                        self.viewModel.addToDoList(title: self.createModel.title, content: self.createModel.content)
                        STToastView.showToastView(str: "create success")
                    }
                    self.resetTextField()
                    self.textField.resignFirstResponder()
                    self.tableView.reloadData()
                } else if field.type == .modify {
                    self.viewModel.modify(content: self.createModel.content, indexPath: self.selectIndexPath ?? nil)
                    STToastView.showToastView(str: "modify success")
                    self.resetTextField()
                    self.textField.resignFirstResponder()
                    self.tableView.reloadData()
                }
            }
        }
    }
        
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        self.textFieldDidEndEditing(textField)
        return true
    }
    
    func deleteToDo(isDelete: Bool, indexPath: IndexPath?) {
        if let index = indexPath {
            var model = self.viewModel.cellForRowAt(section: index.section, row: index.row, isFiltering: isFiltering())
            model.isDeleted = isDelete
            model.isSelected = true
            self.viewModel.didSelectRowAt(section: index.section, row: index.row, model: model)
            self.tableView.reloadData()
            STToastView.showToastView(str: "delete success")
        }
    }
}

extension ViewController: STSelectListViewDelegate {
    func listViewBgBtnClick() {
        self.hiddenSelectView()
    }
    
    func listViewNumberOfRowsInSection() -> Int {
        return self.viewModel.numberOfSections(isFiltering: isFiltering())
    }
    
    func listViewCellForRowAt(row: Int) -> String {
        return self.viewModel.filterGroupName(row: row)
    }
    
    func listViewDidSelectRowAt(row: Int, name: String) {
        self.hiddenSelectView()
        self.textField.text = name
        self.textField.originText = name
        self.createModel.title = name
        self.viewModel.addToDoList(title: self.createModel.title, content: self.createModel.content, isAdd: false, indexPath: IndexPath.init(row: 0, section: row))
        self.resetTextField()
        self.textField.resignFirstResponder()
        self.tableView.reloadData()
        STToastView.showToastView(str: "add success")
    }
}
