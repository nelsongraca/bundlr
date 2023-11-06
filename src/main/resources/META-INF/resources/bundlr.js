class Bundlr {

    rootUrl = "https://bundlr.flowkode.com";

    /**
     * @param {string} projectName Name of the project
     * @param {string} formId ID of the <form> element where the select boxes should be injected
     * @param {string} itemParentTemplate ID of the <template> element that contains the content of the parent element for user selection of a part (example a <select>)
     * @param {string} itemTemplate ID of the <template> element that contains the content for user selection of a part (example an <option>)
     * @param {string} bomHeaderTemplate ID of the <template> element that contains the header of the generated BOM (usually a table)
     * @param {string} bomLineTemplate ID of the <template> element that contains a line of the generated BOM (usually a table)
     */
    constructor(projectName, formId, itemParentTemplate, itemTemplate, bomHeaderTemplate, bomLineTemplate) {
        this.projectName = projectName;
        this.formId = formId;
        this.itemParentTemplate = itemParentTemplate;
        this.itemTemplate = itemTemplate;
        this.bomHeaderTemplate = bomHeaderTemplate;
        this.bomLineTemplate = bomLineTemplate;
    }

    generateZipBundle() {
        this.formElement.action = this.rootUrl + "/generate/bundle/" + this.projectName;
        this.formElement.submit();
    }

    generateBOM() {
        const data = new URLSearchParams();
        for (const pair of new FormData(this.formElement)) {
            data.append(pair[0], pair[1]);
        }
        fetch(this.rootUrl + '/generate/bom/' + this.projectName, {
            method: "POST",
            body: data
        })
            .then(value => value.json())
            .then(jsonData => {

            });
    }

    init() {
        this.formElement = document.getElementById(this.formId);

        const formElement = this.formElement
        fetch(this.rootUrl + '/generate/forminfo/' + this.projectName)
            .then(value => value.json())
            .then(jsonData => {
                let selects = "";
                for (const component of jsonData.components.slice().reverse()) {
                    //build options
                    //empty one
                    let elements = this.fillTemplate(this.itemTemplate, {
                        "{part_id}": "",
                        "{part_name}": component.name
                    });
                    //append the others
                    for (const opt of component.options) {
                        elements = elements + this.fillTemplate(this.itemTemplate, {
                            "{part_id}": opt.id,
                            "{part_name}": opt.value
                        });
                    }
                    //make the parent
                    let selectList = this.fillTemplate(this.itemParentTemplate, {
                        "{component_id}": component.id
                    });
                    //add items
                    selectList = selectList.replace("{items}", elements);
                    selects = selects + selectList;
                }
                //inject into the form
                formElement.innerHTML = formElement.innerHTML.replace("{select}", selects);

            });
        return this;
    }

    fillTemplate(template, dict) {
        const clone = document.getElementById(template).cloneNode(true)
        for (const key in dict) {
            clone.innerHTML = clone.innerHTML.replace(key, dict[key])
        }
        return clone.innerHTML;
    }
}